package com.example.projet.controller.admin;

import com.example.projet.DTO.CreateClientDTO;
import com.example.projet.DTO.CreateUserBody;
import com.example.projet.DTO.RoleRequest;
import com.example.projet.DTO.response.RoleBody;
import com.example.projet.DTO.VisaCardValidator;
import com.example.projet.DTO.response.ClientResponseDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.EqimaClient;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.*;
import com.example.projet.repository.UserRepository;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class FonctionnaireController {
    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EqimaClient eqimaClient;

    public void insertFonctionnaire(Demande demande, List<Integer> roles, Poste poste){
        CreateClientDTO client = new CreateClientDTO();
        client.setExternalId(demande.getTel());
        client.setFirstname(demande.getNom());
        client.setLastname(demande.getPrenom());

        System.out.println(client);
        ClientResponseDTO resultApi = eqimaClient.createClient(client);
        Fonctionnaire fonctionnaire = new Fonctionnaire();
        fonctionnaire.setNom(demande.getNom());
        fonctionnaire.setPrenom(demande.getPrenom());
        fonctionnaire.setAdresse(demande.getAdresse());
        fonctionnaire.setDateNaissance(demande.getDateNaissance());
        fonctionnaire.setTelephone(demande.getTel());
        fonctionnaire.setCIN(demande.getCIN());
        fonctionnaire.setImage(demande.getImage());
        fonctionnaire.setEmail(demande.getEmail());
        fonctionnaire.setPoste(poste);
        fonctionnaire.setIdClient(resultApi.getClientId());
        fonctionnaire.setNumeroVisa(VisaCardValidator.generateVisaCardNumber());
        fonctionnaireService.create(fonctionnaire);

        var bCryptEncoder = new BCryptPasswordEncoder();
        Set<Role> selectedRoles = new HashSet<>();
        for (Integer roleId : roles) {
            Role role = roleService.findById(roleId);
            if (role != null) {
                selectedRoles.add(role);
            }
        }
        System.out.println(selectedRoles);
        Users users = new Users();
            users.setEmail(demande.getEmail());
            users.setRoles(selectedRoles);
            users.setFirstName(demande.getPrenom());
            users.setUsername(demande.getNom());
            users.setPassword(bCryptEncoder.encode("000000"));
            users.setFonctionnaire(fonctionnaire);
        userRepository.save(users);

        CreateUserBody createUserBody = new CreateUserBody();
        createUserBody.setEmail(demande.getEmail());
        createUserBody.setUsername(demande.getPrenom());
        createUserBody.setEnabled(true);
        createUserBody.setFirstName(demande.getNom());
        createUserBody.setLastName(demande.getPrenom());
        Map<String, String> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", "000000");
        credential.put("temporary", "false");

        createUserBody.setCredentials(Collections.singletonList(credential));

        KeyClokResponseToken response = keyCloakClient.getToken();
        Object objet = keyCloakClient.createUser(createUserBody, response.getAccess_token());

        List<UserResponse> userResponse = keyCloakClient.getByUsername(createUserBody.getUsername(),response.getAccess_token());
        UserResponse user = userResponse.get(0);
        for (Integer roleId : roles) {
            Role role = roleService.findById(roleId);
            if (role != null) {
                RoleBody roleBody = keyCloakClient.getRoleByName(role.getRole().name(),response.getAccess_token());
                List<RoleRequest> roleRequests = new ArrayList<>();
                RoleRequest roleRequest = new RoleRequest();
                roleRequest.setId(roleBody.getId());
                roleRequest.setName(roleBody.getName());
                roleRequest.setClientRole(false);
                roleRequest.setContainerId(roleBody.getContainerId());
                roleRequests.add(roleRequest);
                Object mapping = keyCloakClient.roleMapping(user.getId(), response.getAccess_token(), roleRequests);
            }
        }
    }
}
