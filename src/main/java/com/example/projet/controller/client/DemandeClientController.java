package com.example.projet.controller.client;

import com.example.projet.DTO.DemandeFonctionnaireDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.DemandeFonctionnaire;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Notification;
import com.example.projet.model.TypeMission;
import com.example.projet.service.DemandeFonctionnaireService;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.NotificationService;
import com.example.projet.service.TypeMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class DemandeClientController {

    @Autowired
    private FonctionnaireService fonctionnaireService;

    private final String UPLOAD_DIR ="Uploads";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DemandeFonctionnaireService demandeFonctionnaireService;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private TypeMissionService typeMissionService;

    @GetMapping()
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<Notification> notifications = notificationService.allNotification(fonctionnaire.getIdFonctionnaire());
        Integer total = notificationService.total(fonctionnaire.getIdFonctionnaire());
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = "/client";
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("total",total);
        model.addAttribute("previousUrl", referer);
        return "client/Dashboard";
    }

    @GetMapping("/demande")
    public String demande(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }
        model.addAttribute("rolessS",rolesS);
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());
        List<Notification> notifications = notificationService.allNotification(fonctionnaire.getIdFonctionnaire());
        Integer total = notificationService.total(fonctionnaire.getIdFonctionnaire());
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = "/client/demande";
        }

        List<TypeMission> types = typeMissionService.findAllByEtablissement(fonctionnaire.getEtablissement().getIdEtablissement());

        model.addAttribute("notifications", notifications);
        model.addAttribute("total",total);
        model.addAttribute("previousUrl", referer);
        model.addAttribute("types", types);
        DemandeFonctionnaireDTO demandeFonctionnaireDTO = new DemandeFonctionnaireDTO();
        model.addAttribute(demandeFonctionnaireDTO);
        return "client/DemandeJustificatif";
    }


    @PostMapping("/demandes")
    public String demandeSend(
            @RequestParam("document") MultipartFile document,
            @RequestParam("idType") Integer idType,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute DemandeFonctionnaireDTO demandeFonctionnaireDTO,
            BindingResult bindingResult,
            Model model) {


        if (bindingResult.hasErrors()) {
            model.addAttribute("demandeFonctionnaireDTO", demandeFonctionnaireDTO);
            return "client/demande";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }
        TypeMission typeMission = typeMissionService.findById(idType);
        String idUser = authentication.getName();
        KeyClokResponseToken response = keyCloakClient.getToken();
        UserResponse reponseUser = keyCloakClient.getUser(idUser,response.getAccess_token());
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(reponseUser.getEmail());

        if (fonctionnaire == null) {
            redirectAttributes.addFlashAttribute("error", "Fonctionnaire non trouvé.");
            return "redirect:/client/demande";
        }

        if (document.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Le document est obligatoire.");
            return "redirect:/client/demande";
        }

        try {
            String documentName = getFileName(document);
            LocalDateTime now = LocalDateTime.now();
            java.sql.Date dateDemande = java.sql.Date.valueOf(now.toLocalDate());

            if (demandeFonctionnaireDTO.getDateDebut() == null || demandeFonctionnaireDTO.getDateFin() == null) {
                redirectAttributes.addFlashAttribute("error", "Les dates de mission sont obligatoires.");
                return "redirect:/client/demande";
            }

            LocalDate debutLocalDate = demandeFonctionnaireDTO.getDateDebut().toLocalDate();
            LocalDate finLocalDate = demandeFonctionnaireDTO.getDateFin().toLocalDate();


            if (finLocalDate.isBefore(debutLocalDate)) {
                redirectAttributes.addFlashAttribute("error", "La date de fin doit être après la date de début.");
                return "redirect:/client/demande";
            }

            long duree = ChronoUnit.DAYS.between(debutLocalDate, finLocalDate);

            DemandeFonctionnaire demandeFonctionnaire = new DemandeFonctionnaire();
            demandeFonctionnaire.setFonctionnaire(fonctionnaire);
            demandeFonctionnaire.setDateDemande(dateDemande);
            demandeFonctionnaire.setDocument(documentName);
            demandeFonctionnaire.setMotif(demandeFonctionnaireDTO.getMotif());
            demandeFonctionnaire.setDateDebutMission(demandeFonctionnaireDTO.getDateDebut());
            demandeFonctionnaire.setDateFinMission(demandeFonctionnaireDTO.getDateFin());
            demandeFonctionnaire.setDuree((int) duree);
            demandeFonctionnaire.setTypeMission(typeMission);

            demandeFonctionnaireService.create(demandeFonctionnaire);

            redirectAttributes.addFlashAttribute("success", "Votre demande a été envoyée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la demande.");
            e.printStackTrace();
        }

        return "redirect:/client/demande";
    }

    public String getFileName(MultipartFile file){
        try{
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            file.transferTo(filePath);
            return fileName;
        }catch(IOException e){
            e.getMessage();
            return "Erreur lors de l'upload de l'image";
        }
    }

}
