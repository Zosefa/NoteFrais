package com.example.back.controller.Login;

import com.example.back.model.DTO.RegisterDTO;
import com.example.back.model.Role;
import com.example.back.model.Users;
import com.example.back.repository.UserRepository;
import com.example.back.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String login(){
        return "login/Login";
    }

    @GetMapping("/inscription")
    public String register(Model model){
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        model.addAttribute("success", false);
        return "login/Inscription";
    }

    @PostMapping("/inscription")
    public String postRegister(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result){
        if(!registerDTO.getPassword().equals(registerDTO.getConfirm())){
            result.addError(
                    new FieldError("registerDTO","confirmation mot de passe", "Le mot de passe dois etre bien confirmer")
            );
        }

        Users users = userRepository.findByEmail(registerDTO.getEmail());

        if(users != null){
            result.addError(
                    new FieldError("registerDTO","email", "L'Email est dejas utilisé")
            );
        }

        if(result.hasErrors()){
            return "login/Inscription";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();
            Role defaultRole = roleService.findByRoleName("ADMIN");

            if (defaultRole == null) {
                throw new IllegalArgumentException("Le rôle par défaut 'USER' n'existe pas dans la base de données.");
            }

            Set<Role> roles = new HashSet<>();
            roles.add(defaultRole);
            Users usersave = new Users();
            usersave.setEmail(registerDTO.getEmail());
            usersave.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            usersave.setRoles(roles);
            userRepository.save(usersave);

            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
        }catch(Exception e){
            result.addError(
                    new FieldError("registerDTO","email", e.getMessage())
            );
        }
        return "login/Login";
    }
}
