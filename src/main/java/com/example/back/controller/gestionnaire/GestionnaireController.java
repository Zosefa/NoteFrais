package com.example.back.controller.gestionnaire;

import com.example.back.client.EqimaClient;
import com.example.back.model.DTO.SavingsAccountResponse;
import com.example.back.model.DemandeFonctionnaire;
import com.example.back.model.DemandeFonctionnaireRefuser;
import com.example.back.model.DemandeFonctionnaireValider;
import com.example.back.model.Fonctionnaire;
import com.example.back.model.ResultDTO.ResultDemandeFonctionnaireDTO;
import com.example.back.service.DemandeFonctionnaireRefuserService;
import com.example.back.service.DemandeFonctionnaireService;
import com.example.back.service.DemandeFonctionnaireValiderService;
import com.example.back.service.FonctionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/gestionnaire")
public class GestionnaireController {
    @Autowired
    private DemandeFonctionnaireService demandeFonctionnaireService;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private DemandeFonctionnaireValiderService demandeFonctionnaireValiderService;

    @Autowired
    private DemandeFonctionnaireRefuserService demandeFonctionnaireRefuserService;

    @Autowired
    private EqimaClient eqimaClient;

    @GetMapping
    public String gestionnaire(){
        return ("Gestionnaire/Dashboard");
    }

    @GetMapping("/demande")
    public String demandeFonctionnaire(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(email);
        List<ResultDemandeFonctionnaireDTO> demandes = demandeFonctionnaireService.findDemandeFonctionnaire(fonctionnaire.getIdFonctionnaire());
        model.addAttribute("demandes",demandes);
        return ("Gestionnaire/DemandeFonctionnaire");
    }

    @PostMapping("/demande-valider/{id}")
    public String validation(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        DemandeFonctionnaire demandeFonctionnaire = demandeFonctionnaireService.findById(id);
        if(demandeFonctionnaire != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateValidation = new java.sql.Date(dateValidationUtil.getTime());

            DemandeFonctionnaireValider demandeFonctionnaireValider = new DemandeFonctionnaireValider();
            demandeFonctionnaireValider.setDateValidation(dateValidation);
            demandeFonctionnaireValider.setDemandeFonctionnaire(demandeFonctionnaire);

            demandeFonctionnaireValiderService.create(demandeFonctionnaireValider);
            redirectAttributes.addFlashAttribute("success", "La demande a été validée avec succès !");
        }else{
            redirectAttributes.addFlashAttribute("erreur", "Demande non trouver !");
        }
        return ("redirect:/gestionnaire/demande");
    }

    @PostMapping("/demande-refuser/{id}")
    public String refus(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        DemandeFonctionnaire demandeFonctionnaire = demandeFonctionnaireService.findById(id);
        if(demandeFonctionnaire != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateRefus = new java.sql.Date(dateValidationUtil.getTime());

            DemandeFonctionnaireRefuser demandeFonctionnaireRefuser = new DemandeFonctionnaireRefuser();
            demandeFonctionnaireRefuser.setDateRefus(dateRefus);
            demandeFonctionnaireRefuser.setDemandeFonctionnaire(demandeFonctionnaire);

            demandeFonctionnaireRefuserService.create(demandeFonctionnaireRefuser);
            redirectAttributes.addFlashAttribute("success", "La demande a été refusé avec succès !");
        }else{
            redirectAttributes.addFlashAttribute("erreur", "Demande non trouver !");
        }
        return ("redirect:/gestionnaire/demande");
    }

    @GetMapping("/misa-solde")
    public String misaSolde(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(email);
        SavingsAccountResponse result = eqimaClient.accountBalance(fonctionnaire.getIdClient());
        Double solde = result.getSavingsAccounts()[0].getAccountBalance();
        model.addAttribute("solde", solde);
        return "Gestionnaire/solde";
    }
}
