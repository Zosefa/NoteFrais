package com.example.projet.controller.client;

import com.example.projet.DTO.ProfilDTO;
import com.example.projet.DTO.UpdatePasswordBody;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Notification;
import com.example.projet.model.Users;
import com.example.projet.repository.UserRepository;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ProfilClient {
    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/profil")
    public String profil(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        ProfilDTO profilDTO = new ProfilDTO();
        profilDTO.setEmail(reponseUser.getEmail());
        model.addAttribute("email",reponseUser.getEmail());
        model.addAttribute(profilDTO);

        if (authentication != null) {
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<Notification> notifications = notificationService.allNotification(fonctionnaire.getIdFonctionnaire());
        Integer total = notificationService.total(fonctionnaire.getIdFonctionnaire());
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = "/client/profil";
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("total",total);
        model.addAttribute("previousUrl", referer);

        return "client/Profil";
    }

    @PostMapping("/profil")
    public String profilUpdate(@Valid @ModelAttribute ProfilDTO profilDTO, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        model.addAttribute("email",reponseUser.getEmail());
        Users users = userRepository.findByEmail(profilDTO.getEmail());
        if(users != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(profilDTO.getPassword(), users.getPassword())){
                if(profilDTO.getNewPassword().equals(profilDTO.getConfirm())){
                    users.setPassword(encoder.encode(profilDTO.getNewPassword()));
                    userRepository.save(users);

                    UpdatePasswordBody updatePasswordBody = new UpdatePasswordBody();
                    updatePasswordBody.setValue(profilDTO.getNewPassword());

                    keyCloakClient.updatePassword(idUser, response.getAccess_token(), updatePasswordBody);

                    model.addAttribute("profilDTO", new ProfilDTO());
                    model.addAttribute("success","profil modifier avec success");
                }else{
                    model.addAttribute("erreur","veuillez bien confirmer votre mot de passe");
                }
            }else{
                model.addAttribute("erreur","veuillez entrer votre mot de passe");
            }
        }
        return "redirect:/client/profil";
    }
}
