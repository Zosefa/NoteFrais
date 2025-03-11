package com.example.projet.controller.admin;

import com.example.projet.DTO.PosteDTO;
import com.example.projet.DTO.TypeMissionDTO;
import com.example.projet.model.Poste;
import com.example.projet.model.TypeMission;
import com.example.projet.service.TypeMissionService;
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
public class TypeMissionController {
    @Autowired
    private TypeMissionService typeMissionService;

    @GetMapping("/type-mission")
    public String poste(Model model){
        List<TypeMission> types = typeMissionService.findAll();
        model.addAttribute("types", types);
        model.addAttribute("typeMissionDTO", new TypeMissionDTO());
        return ("admin/page/TypeMission");
    }

    @PostMapping("/type-mission")
    public String posteCreate(@Valid @ModelAttribute TypeMissionDTO typeMissionDTO, Model model, RedirectAttributes redirectAttributes){
        List<TypeMission> types = typeMissionService.findAll();
        model.addAttribute("types", types);
        try {
            TypeMission type = new TypeMission();
            type.setTypeMission(typeMissionDTO.getTypeMission());
            typeMissionService.create(type);
            model.addAttribute("typeMissionDTO", new TypeMissionDTO());
            redirectAttributes.addFlashAttribute("success","Type Mission ajouter!");
        }catch(Exception e){
            e.printStackTrace();
        }
        return ("redirect:/dashboard/type-mission");
    }
}
