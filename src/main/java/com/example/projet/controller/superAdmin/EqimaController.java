package com.example.projet.controller.superAdmin;

import com.example.projet.DTO.CreateClientDTO;
import com.example.projet.DTO.CreateUserBody;
import com.example.projet.DTO.FonctionnaireDTO;
import com.example.projet.DTO.RoleRequest;
import com.example.projet.DTO.response.ClientResponseDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.RoleBody;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.EqimaClient;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Etablissement;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Role;
import com.example.projet.model.Users;
import com.example.projet.repository.UserRepository;
import com.example.projet.service.EtablissementService;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/eqima")
public class EqimaController {
    @Autowired
    private EtablissementService etablissementService;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private EqimaClient eqimaClient;


    @GetMapping
    public String eqima(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);

        return ("superAdmin/dashboard");
    }

    @GetMapping("/etablissement")
    public String etablissement(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        List<Role> roles = roleService.findAll();
        model.addAttribute("rolessS",rolesS);
        model.addAttribute("fonctionnaireDTO", new FonctionnaireDTO());

        return ("superAdmin/etablissement");
    }

    @PostMapping("/etablissement")
    public String postEtablissement(
            @Valid @ModelAttribute FonctionnaireDTO fonctionnaireDTO,
            Model model, RedirectAttributes redirectAttributes,
            @RequestParam("etablissement") String etablissement){
        Etablissement etablissement1 = new Etablissement();
        etablissement1.setEtablissement(etablissement);
        etablissementService.create(etablissement1);

        CreateClientDTO client = new CreateClientDTO();
        client.setExternalId(fonctionnaireDTO.getTel());
        client.setFirstname(fonctionnaireDTO.getNom());
        client.setLastname(fonctionnaireDTO.getPrenom());

        ClientResponseDTO resultApi = eqimaClient.createClient(client);

        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaire.setNom(fonctionnaireDTO.getNom());
        fonctionnaire.setPrenom(fonctionnaireDTO.getPrenom());
        fonctionnaire.setAdresse(fonctionnaireDTO.getAdresse());
        fonctionnaire.setCIN(fonctionnaireDTO.getCIN());
        fonctionnaire.setTelephone(fonctionnaireDTO.getTel());
        fonctionnaire.setEmail(fonctionnaireDTO.getEmail());
        fonctionnaire.setDateNaissance(fonctionnaireDTO.getDate());
        fonctionnaire.setEtablissement(etablissement1);
        fonctionnaire.setIdClient(resultApi.getClientId());
        fonctionnaireService.create(fonctionnaire);

        var bCryptEncoder = new BCryptPasswordEncoder();
        Set<Role> selectedRoles = new HashSet<>();
        Role role = roleService.findByRoleName("ADMIN");

        selectedRoles.add(role);

        Users users = new Users();
        users.setUsername(fonctionnaire.getPrenom());
        users.setEmail(fonctionnaire.getEmail());
        users.setFonctionnaire(fonctionnaire);
        users.setFirstName(fonctionnaire.getNom());
        users.setPassword(bCryptEncoder.encode("000000"));
        users.setRoles(selectedRoles);
        userRepository.save(users);


        CreateUserBody createUserBody = new CreateUserBody();
        createUserBody.setEmail(fonctionnaire.getEmail());
        createUserBody.setUsername(fonctionnaire.getPrenom());
        createUserBody.setEnabled(true);
        createUserBody.setFirstName(fonctionnaire.getNom());
        createUserBody.setLastName(fonctionnaire.getPrenom());
        Map<String, String> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", "000000");
        credential.put("temporary", "false");

        createUserBody.setCredentials(Collections.singletonList(credential));

        KeyClokResponseToken response = keyCloakClient.getToken();
        Object objet = keyCloakClient.createUser(createUserBody, response.getAccess_token());

        List<UserResponse> userResponse = keyCloakClient.getByUsername(createUserBody.getUsername(),response.getAccess_token());
        UserResponse user = userResponse.get(0);

        RoleBody roleBody = keyCloakClient.getRoleByName(role.getRole().name(),response.getAccess_token());
        List<RoleRequest> roleRequests = new ArrayList<>();
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setId(roleBody.getId());
        roleRequest.setName(roleBody.getName());
        roleRequest.setClientRole(false);
        roleRequest.setContainerId(roleBody.getContainerId());
        roleRequests.add(roleRequest);
        Object mapping = keyCloakClient.roleMapping(user.getId(), response.getAccess_token(), roleRequests);

        redirectAttributes.addFlashAttribute("success","Etablissement ajouter!");

        return ("redirect:/eqima/etablissement");
    }
}
