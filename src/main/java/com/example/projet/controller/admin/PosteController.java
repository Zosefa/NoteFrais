package com.example.projet.controller.admin;

import com.example.projet.DTO.PosteDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Poste;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.PosteService;
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
public class PosteController {
    @Autowired
    private PosteService posteService;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @GetMapping("/poste")
    public String poste(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        List<Poste> postes = posteService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("postes", postes);
        model.addAttribute("posteDTO", new PosteDTO());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);

        return ("admin/page/Poste");
    }

    @PostMapping("/poste")
    public String posteCreate(@Valid @ModelAttribute PosteDTO posteDTO, Model model, RedirectAttributes redirectAttributes){
        List<Poste> postes = posteService.findAll();
        model.addAttribute("postes", postes);
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String idUser = authentication.getName();
            KeyClokResponseToken response = keyCloakClient.getToken();
            UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
            Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
            Poste poste = new Poste();
            poste.setPoste(posteDTO.getPoste());
            poste.setEtablissement(fonctionnaire.getEtablissement());
            posteService.create(poste);
            model.addAttribute("posteDTO", new PosteDTO());
            redirectAttributes.addFlashAttribute("success","poste ajouter!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ("redirect:/dashboard/poste");
    }

    @PostMapping("/poste/{id}")
    public String posteUpdate(@PathVariable("id") Integer id, @RequestParam("nom") String nom, RedirectAttributes redirectAttributes){
        Poste poste = posteService.findById(id);
        poste.setPoste(nom);
        posteService.update(poste);
        redirectAttributes.addFlashAttribute("success", "modification effcetuer!");
        return("redirect:/dashboard/poste");
    }

    @PostMapping("/poste/delete/{id}")
    public String deletePoste(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        posteService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Suppression effcetuer!");
        return("redirect:/dashboard/poste");
    }
}
