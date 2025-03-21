package com.example.projet.controller.admin;

import com.example.projet.DTO.IndemniteDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Indemnite;
import com.example.projet.model.Poste;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.IndemniteService;
import com.example.projet.service.PosteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class IndemniteController {

    @Autowired
    private PosteService posteService;

    @Autowired
    private IndemniteService indemniteService;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @GetMapping("indemnite")
    public String indemnite(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        List<Indemnite> indemnites = indemniteService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        List<Poste> postes = posteService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("indemnites", indemnites);
        model.addAttribute("indemniteDTO", new IndemniteDTO());
        model.addAttribute("postes",postes);

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);

        return ("admin/page/Indemnite");
    }

    @PostMapping("/indemnite")
    public String indemniteCreate(@Valid @ModelAttribute IndemniteDTO indemniteDTO, Model model, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());


        List<Indemnite> indemnites = indemniteService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        List<Poste> postes = posteService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());
        model.addAttribute("indemnites", indemnites);
        model.addAttribute("indemniteDTO", new IndemniteDTO());
        model.addAttribute("postes",postes);
        try {
            Poste poste = posteService.findById(indemniteDTO.getPoste());
            if(poste != null){
                Indemnite indemnite = new Indemnite();
                indemnite.setIndemnite(indemniteDTO.getIndemnite());
                indemnite.setPoste(poste);
                indemniteService.create(indemnite);
                redirectAttributes.addFlashAttribute("success","Indemnite ajouter!");
            }else{
                model.addAttribute("erreur","poste non trouver");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return ("redirect:/dashboard/indemnite");
    }
}
