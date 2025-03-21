package com.example.projet.controller.admin;

import com.example.projet.DTO.TypeMissionDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Poste;
import com.example.projet.model.TypeMission;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.TypeMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class TypeMissionController {
    @Autowired
    private TypeMissionService typeMissionService;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @GetMapping("/type-mission")
    public String poste(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        List<TypeMission> types = typeMissionService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("types", types);
        model.addAttribute("typeMissionDTO", new TypeMissionDTO());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);

        return ("admin/page/TypeMission");
    }

    @PostMapping("/type-mission")
    public String posteCreate(@Valid @ModelAttribute TypeMissionDTO typeMissionDTO, Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        List<TypeMission> types = typeMissionService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("types", types);
        try {
            TypeMission type = new TypeMission();
            type.setTypeMission(typeMissionDTO.getTypeMission());
            type.setEtablissement(fonctionnaire.getEtablissement());
            typeMissionService.create(type);
            model.addAttribute("typeMissionDTO", new TypeMissionDTO());
            redirectAttributes.addFlashAttribute("success","Type Mission ajouter!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ("redirect:/dashboard/type-mission");
    }

    @PostMapping("/type-mission/{id}")
    public String typeUpdate(@PathVariable("id") Integer id, @RequestParam("nom") String nom, RedirectAttributes redirectAttributes){
        TypeMission typeMission = typeMissionService.findById(id);
        typeMission.setTypeMission(nom);
        typeMissionService.update(typeMission);
        redirectAttributes.addFlashAttribute("success", "modification effcetuer!");
        return("redirect:/dashboard/type-mission");
    }

    @PostMapping("/type-mission/delete/{id}")
    public String deleteType(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        typeMissionService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Suppression effcetuer!");
        return("redirect:/dashboard/type-mission");
    }
}
