package com.example.projet.controller.admin;

import com.example.projet.DTO.PosteDTO;
import com.example.projet.model.Poste;
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
public class PosteController {
    @Autowired
    private PosteService posteService;

    @GetMapping("/poste")
    public String poste(Model model){
        List<Poste> postes = posteService.findAll();
        model.addAttribute("postes", postes);
        model.addAttribute("posteDTO", new PosteDTO());
        return ("admin/page/Poste");
    }

    @PostMapping("/poste")
    public String posteCreate(@Valid @ModelAttribute PosteDTO posteDTO, Model model, RedirectAttributes redirectAttributes){
        List<Poste> postes = posteService.findAll();
        model.addAttribute("postes", postes);
        try {
            Poste poste = new Poste();
            poste.setPoste(posteDTO.getPoste());
            posteService.create(poste);
            model.addAttribute("posteDTO", new PosteDTO());
            redirectAttributes.addFlashAttribute("success","poste ajouter!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ("redirect:/dashboard/poste");
    }
}
