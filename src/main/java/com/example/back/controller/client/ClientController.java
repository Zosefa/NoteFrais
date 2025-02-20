package com.example.back.controller.client;

import com.example.back.client.EqimaClient;
import com.example.back.model.DTO.DemandeDTO;
import com.example.back.model.DTO.DemandeFonctionnaireDTO;
import com.example.back.model.DTO.ProfilDTO;
import com.example.back.model.DTO.SavingsAccountResponse;
import com.example.back.model.DemandeFonctionnaire;
import com.example.back.model.Fonctionnaire;
import com.example.back.model.Users;
import com.example.back.repository.UserRepository;
import com.example.back.service.DemandeFonctionnaireService;
import com.example.back.service.FonctionnaireService;
import com.example.back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private DemandeFonctionnaireService demandeFonctionnaireService;

    @Autowired
    private EqimaClient eqimaClient;

    private final String UPLOAD_DIR ="Uploads";

    @GetMapping
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model){

        if (userDetails != null) {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }
        return "client/Dashboard";
    }

    @GetMapping("/demande")
    public String demande(Model model,@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails != null) {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }
        DemandeFonctionnaireDTO demandeFonctionnaireDTO = new DemandeFonctionnaireDTO();
        model.addAttribute(demandeFonctionnaireDTO);
        return "client/DemandeJustificatif";
    }


    @PostMapping("/demandes")
    public String demandeSend(
            @RequestParam("document") MultipartFile document,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute DemandeFonctionnaireDTO demandeFonctionnaireDTO,
            BindingResult bindingResult,
            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("demandeFonctionnaireDTO", demandeFonctionnaireDTO);
            return "client/demande";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(email);

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

            demandeFonctionnaireService.create(demandeFonctionnaire);

            redirectAttributes.addFlashAttribute("success", "Votre demande a été envoyée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite lors de l'envoi de la demande.");
            e.printStackTrace();
        }

        return "redirect:/client/demande";
    }



    @GetMapping("/profil")
    public String profil(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ProfilDTO profilDTO = new ProfilDTO();
        profilDTO.setEmail(email);
        model.addAttribute("email",email);
        model.addAttribute(profilDTO);

        if (userDetails != null) {
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            model.addAttribute("roles", roles);
        }

        return "client/Profil";
    }

    @PostMapping("/profil")
    public String profilUpdate(@Valid @ModelAttribute ProfilDTO profilDTO,Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        model.addAttribute("email",email);
        Users users = userRepository.findByEmail(profilDTO.getEmail());
        System.out.println(profilDTO.getPassword());
        if(users != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(profilDTO.getPassword(), users.getPassword())){
                if(profilDTO.getNewPassword().equals(profilDTO.getConfirm())){
                    users.setPassword(encoder.encode(profilDTO.getNewPassword()));
                    userRepository.save(users);
                    model.addAttribute("profilDTO", new ProfilDTO());
                    model.addAttribute("success","profil modifier avec success");
                }else{
                    model.addAttribute("erreur","veuillez bien confirmer votre mot de passe");
                }
            }else{
                model.addAttribute("erreur","veuillez entrer votre mot de passe");
            }
        }
        return "client/profil";
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

    @GetMapping("/misa-solde")
    public String misaSolde(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Fonctionnaire fonctionnaire = fonctionnaireService.findByEmail(email);
        SavingsAccountResponse result = eqimaClient.accountBalance(fonctionnaire.getIdClient());
        Double solde = result.getSavingsAccounts()[0].getAccountBalance();
        model.addAttribute("solde", solde);
        return "client/solde";
    }
}
