package com.example.projet.controller.gestionnaire;

import com.example.projet.DTO.TransfersBody;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.ResultDemandeFonctionnaireDTO;
import com.example.projet.DTO.response.SavingsAccountResponse;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.EqimaClient;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.*;
import com.example.projet.repository.UserRepository;
import com.example.projet.service.DemandeFonctionnaireRefuserService;
import com.example.projet.service.DemandeFonctionnaireService;
import com.example.projet.service.DemandeFonctionnaireValiderService;
import com.example.projet.service.FonctionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/gestionnaire")
public class DemandeFonctionnaireController {

    @Autowired
    private DemandeFonctionnaireService demandeFonctionnaireService;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DemandeFonctionnaireValiderService demandeFonctionnaireValiderService;

    @Autowired
    private DemandeFonctionnaireRefuserService demandeFonctionnaireRefuserService;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private EqimaClient eqimaClient;


    @GetMapping
    public String gestionnaire(){
        return ("Gestionnaire/Dashboard");
    }

    @GetMapping("/demande")
    public String demandeFonctionnaire(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<ResultDemandeFonctionnaireDTO> demandes = demandeFonctionnaireService.findDemandeFonctionnaire(fonctionnaire.getIdFonctionnaire());
        model.addAttribute("demandes",demandes);
        return ("Gestionnaire/DemandeFonctionnaire");
    }

    @PostMapping("/demande-valider/{id}")
    public String validation(@PathVariable Integer id, RedirectAttributes redirectAttributes, @RequestParam("total") String total){
        DemandeFonctionnaire demandeFonctionnaire = demandeFonctionnaireService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Users users = userRepository.findByEmail(reponseUser.getEmail());
        if(demandeFonctionnaire != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateValidation = new java.sql.Date(dateValidationUtil.getTime());

            DemandeFonctionnaireValider demandeFonctionnaireValider = new DemandeFonctionnaireValider();
            demandeFonctionnaireValider.setDateValidation(dateValidation);
            demandeFonctionnaireValider.setDemandeFonctionnaire(demandeFonctionnaire);
            demandeFonctionnaireValider.setUsers(users);

            demandeFonctionnaireValiderService.create(demandeFonctionnaireValider);

            notificationController.insert(demandeFonctionnaire,true);

            Fonctionnaire fonctionnaire = fonctionnaireService.findById(demandeFonctionnaire.getFonctionnaire().getIdFonctionnaire());
            SavingsAccountResponse result = eqimaClient.accountBalance(fonctionnaire.getIdClient());

            Integer idAcount = result.getSavingsAccounts()[0].getId();
            TransfersBody transfersBody = new TransfersBody();
            transfersBody.setToAccountId(idAcount);
            transfersBody.setTransferAmount(total);

            eqimaClient.transfert(transfersBody);

            redirectAttributes.addFlashAttribute("success", "La demande a été validée avec succès !");
        }else{
            redirectAttributes.addFlashAttribute("erreur", "Demande non trouver !");
        }
        return ("redirect:/gestionnaire/demande");
    }

    @PostMapping("/demande-refuser/{id}")
    public String refus(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        DemandeFonctionnaire demandeFonctionnaire = demandeFonctionnaireService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Users users = userRepository.findByEmail(reponseUser.getEmail());
        if(demandeFonctionnaire != null){
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateRefus = new java.sql.Date(dateValidationUtil.getTime());

            DemandeFonctionnaireRefuser demandeFonctionnaireRefuser = new DemandeFonctionnaireRefuser();
            demandeFonctionnaireRefuser.setDateRefus(dateRefus);
            demandeFonctionnaireRefuser.setDemandeFonctionnaire(demandeFonctionnaire);
            demandeFonctionnaireRefuser.setUsers(users);

            demandeFonctionnaireRefuserService.create(demandeFonctionnaireRefuser);
            notificationController.insert(demandeFonctionnaire,false);
            redirectAttributes.addFlashAttribute("success", "La demande a été refusé avec succès !");
        }else{
            redirectAttributes.addFlashAttribute("erreur", "Demande non trouver !");
        }
        return ("redirect:/gestionnaire/demande");
    }
}
