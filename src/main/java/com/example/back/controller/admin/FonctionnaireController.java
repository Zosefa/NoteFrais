package com.example.back.controller.admin;

import com.example.back.client.EqimaClient;
import com.example.back.model.*;
import com.example.back.model.DTO.CreateClientDTO;
import com.example.back.model.DTO.VisaCardValidator;
import com.example.back.model.ResultDTO.ClientResponseDTO;
import com.example.back.repository.FonctionnaireRepository;
import com.example.back.repository.UserRepository;
import com.example.back.service.FonctionnaireService;
import com.example.back.service.RoleService;
import com.example.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("fonctionnaire")
public class FonctionnaireController {

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EqimaClient eqimaClient;

    public void insertFonctionnaire(Demande demande, List<Integer> roles, Poste poste){
        CreateClientDTO client = new CreateClientDTO();
        client.setExternalId(demande.getTel());
        client.setFirstname(demande.getNom());
        client.setLastname(demande.getPrenom());
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
            users.setPassword(bCryptEncoder.encode("000000"));
            users.setFonctionnaire(fonctionnaire);
        userRepository.save(users);
    }
}
