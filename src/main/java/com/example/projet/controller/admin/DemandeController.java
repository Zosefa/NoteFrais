package com.example.projet.controller.admin;

import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.*;
import com.example.projet.repository.UserRepository;
import com.example.projet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DemandeController {
    @Autowired
    private DemandeService demandeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PosteService posteService;

    @Autowired
    private DemandeAccorderService demandeAccorderService;

    @Autowired
    private DemandeRefuserService demandeRefuserService;

    @Autowired
    private FonctionnaireController fonctionnaireController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private FonctionnaireService fonctionnaireService;




    @GetMapping
    public String dashboard(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        String id = authentication.getName();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        model.addAttribute("rolessS",rolesS);
        return ("admin/Dashboard");
    }

    @GetMapping("/demande")
    public String demande(Model model){

        List<Role> roles = roleService.findAll();
        List<Poste> postes = posteService.findAll();

        model.addAttribute("roles",roles);
        model.addAttribute("postes",postes);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        List<Demande> demandes = demandeService.findDemandeNonSoumise(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("demandes",demandes);

        model.addAttribute("rolessS",rolesS);

        return ("admin/page/Demande");
    }

    @PostMapping("/validation/{id}")
    public String validation(@PathVariable Integer id, @RequestParam List<Integer> roles, @RequestParam Integer poste, RedirectAttributes redirectAttributes){
        Demande demande = demandeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Users users = userRepository.findByEmail(reponseUser.getEmail());
        Poste postes = posteService.findById(poste);
        if(demande != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateValidation = new java.sql.Date(dateValidationUtil.getTime());
            Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

            DemandeAccorder demandeAccorder = new DemandeAccorder();
            demandeAccorder.setDemande(demande);
            demandeAccorder.setDateValidation(dateValidation);
            demandeAccorder.setUser(users);
            demandeAccorderService.insert(demandeAccorder);

            fonctionnaireController.insertFonctionnaire(demande, roles, postes, fonctionnaire.getEtablissement());
            redirectAttributes.addFlashAttribute("success", "La demande a été validée avec succès !");
        }
        return ("redirect:/dashboard/demande");
    }

    @PostMapping("/refus/{id}")
    public String refus(@PathVariable Integer id,RedirectAttributes redirectAttributes){

        Demande demande = demandeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Users users = userRepository.findByEmail(reponseUser.getEmail());
        if(demande != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateRefusUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateRefus = new java.sql.Date(dateRefusUtil.getTime());

            DemandeRefuser demandeRefuser = new DemandeRefuser();
            demandeRefuser.setDemande(demande);
            demandeRefuser.setDateRefus(dateRefus);
            demandeRefuser.setUser(users);
            demandeRefuserService.insert(demandeRefuser);
            redirectAttributes.addFlashAttribute("success", "La demande a été refusé !");
        }
        return ("redirect:/dashboard/demande");
    }

    @GetMapping("/demande-accorder")
    public String demandeAccorder(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<DemandeAccorder> demandes = demandeAccorderService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        model.addAttribute("rolessS",rolesS);
        model.addAttribute("demandes",demandes);
        return ("admin/page/DemandeAccorder");
    }

    @GetMapping("/demande-refuser")
    public String demandeRefuser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<DemandeRefuser> demandes = demandeRefuserService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        model.addAttribute("rolessS",rolesS);
        model.addAttribute("demandes",demandes);
        return ("admin/page/DemandeRefuser");
    }
}
