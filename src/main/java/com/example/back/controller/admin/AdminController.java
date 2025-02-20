package com.example.back.controller.admin;

import com.example.back.client.EqimaClient;
import com.example.back.model.*;
import com.example.back.model.DTO.DemandeFonctionnaireDTO;
import com.example.back.model.DTO.IndemniteDTO;
import com.example.back.model.DTO.PosteDTO;
import com.example.back.model.ResultDTO.ResultDemandeFonctionnaireDTO;
import com.example.back.repository.UserRepository;
import com.example.back.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class AdminController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DemandeAccorderService demandeAccorderService;

    @Autowired
    private DemandeRefuserService demandeRefuserService;

    @Autowired
    private FonctionnaireController fonctionnaireController;

    @Autowired
    private DemandeFonctionnaireService demandeFonctionnaireService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PosteService posteService;

    @Autowired
    private IndemniteService indemniteService;

    @GetMapping
    public String dashboard(){
        return ("admin/Dashboard");
    }

    @GetMapping("/demande")
    public String demande(Model model){
        List<Demande> demandes = demandeService.findDemandeNonSoumise();
        List<Role> roles = roleService.findAll();
        List<Poste> postes = posteService.findAll();
        model.addAttribute("demandes",demandes);
        model.addAttribute("roles",roles);
        model.addAttribute("postes",postes);
        return ("admin/page/Demande");
    }

    @GetMapping("/demande-fonctionaire")
    public String demandefonctionnaire(Model model){
        List<ResultDemandeFonctionnaireDTO> demandes = demandeFonctionnaireService.findDemandeFonctionnaire(3);
        model.addAttribute("demandes",demandes);
        return ("admin/page/DemandeFonctionnaire");
    }

    @PostMapping("/validation/{id}")
    public String validation(@PathVariable Integer id,@RequestParam List<Integer> roles,@RequestParam Integer poste, RedirectAttributes redirectAttributes){
        Demande demande = demandeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Users users = userRepository.findByEmail(email);
        Poste postes = posteService.findById(poste);
        if(demande != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateValidation = new java.sql.Date(dateValidationUtil.getTime());

            DemandeAccorder demandeAccorder = new DemandeAccorder();
            demandeAccorder.setDemande(demande);
            demandeAccorder.setDateValidation(dateValidation);
            demandeAccorder.setUser(users);
            demandeAccorderService.insert(demandeAccorder);

            fonctionnaireController.insertFonctionnaire(demande, roles, postes);
            redirectAttributes.addFlashAttribute("success", "La demande a été validée avec succès !");
        }
        return ("redirect:/dashboard/demande");
    }

    @PostMapping("/refus/{id}")
    public String refus(@PathVariable Integer id,RedirectAttributes redirectAttributes){

        Demande demande = demandeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        Users users = userRepository.findByEmail(email);
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
        List<DemandeAccorder> demandes = demandeAccorderService.findAll();
        model.addAttribute("demandes",demandes);
        return ("admin/page/DemandeAccorder");
    }

    @GetMapping("/demande-refuser")
    public String demandeRefuser(Model model){
        List<DemandeRefuser> demandes = demandeRefuserService.findAll();
        model.addAttribute("demandes",demandes);
        return ("admin/page/DemandeRefuser");
    }

    @GetMapping("/poste")
    public String poste(Model model){
        List<Poste> postes = posteService.findAll();
        model.addAttribute("postes", postes);
        model.addAttribute("posteDTO", new PosteDTO());
        return ("admin/page/Poste");
    }

    @PostMapping("/poste")
    public String posteCreate(@Valid @ModelAttribute PosteDTO posteDTO,Model model,RedirectAttributes redirectAttributes){
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
    public String indemniteCreate(@Valid @ModelAttribute IndemniteDTO indemniteDTO,Model model,RedirectAttributes redirectAttributes){
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
