package com.example.projet.controller.admin;

import com.example.projet.DTO.IndemniteDTO;
import com.example.projet.model.Indemnite;
import com.example.projet.model.Poste;
import com.example.projet.service.IndemniteService;
import com.example.projet.service.PosteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class IndemniteController {

    @Autowired
    private PosteService posteService;

    @Autowired
    private IndemniteService indemniteService;

    @GetMapping("indemnite")
    public String indemnite(Model model){
        List<Indemnite> indemnites = indemniteService.findAll();
        List<Poste> postes = posteService.findAll();
        model.addAttribute("indemnites", indemnites);
        model.addAttribute("indemniteDTO", new IndemniteDTO());
        model.addAttribute("postes",postes);
        return ("admin/page/Indemnite");
    }

    @PostMapping("/indemnite")
    public String indemniteCreate(@Valid @ModelAttribute IndemniteDTO indemniteDTO, Model model, RedirectAttributes redirectAttributes){
        List<Indemnite> indemnites = indemniteService.findAll();
        List<Poste> postes = posteService.findAll();
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
