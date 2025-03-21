package com.example.projet.controller.client;

import com.example.projet.DTO.DemandeDTO;
import com.example.projet.model.Demande;
import com.example.projet.model.Etablissement;
import com.example.projet.service.DemandeService;
import com.example.projet.service.EtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping
public class DossierController {

    @Autowired
    private DemandeService demandeService;

    @Autowired
    private EtablissementService etablissementService;

    private final String UPLOAD_DIR ="Uploads";

    @GetMapping("/")
    public String inscription(Model model){
        DemandeDTO demandeDTO = new DemandeDTO();
        model.addAttribute(demandeDTO);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> rolesS = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            rolesS.add(authority.getAuthority());
        }

        List<Etablissement> etablissements = etablissementService.findAll();
        model.addAttribute("rolessS",rolesS);
        model.addAttribute("etablissements",etablissements);
        return "client/Demande";
    }

    @PostMapping("/envoie")
    public String envoie(
            @Valid @ModelAttribute DemandeDTO demandeDTO,
            BindingResult result,
            @RequestParam("image") MultipartFile imageFile,
            Model model,
            @RequestParam("imageCIN") MultipartFile imageCINFile,
            @RequestParam("etablissement") Integer etablissementId){
        try {
            LocalDateTime now = LocalDateTime.now();
            java.util.Date dateValidationUtil = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date dateDemande = new java.sql.Date(dateValidationUtil.getTime());
            Etablissement etablissement = etablissementService.findById(etablissementId);
            if(!imageFile.isEmpty()){
                Demande demande = new Demande();

                String fileName = getFileName(imageFile);
                String fileCinImage = getFileName(imageCINFile);
                demande.setImage("Uploads/"+fileName);
                demande.setImageCIN(fileCinImage);
                demande.setNom(demandeDTO.getNom());
                demande.setPrenom(demandeDTO.getPrenom());
                demande.setAdresse(demandeDTO.getAdresse());
                demande.setTel(demandeDTO.getTel());
                demande.setCIN(demandeDTO.getCIN());
                demande.setDateNaissance(demandeDTO.getDate());
                demande.setEmail(demandeDTO.getEmail());
                demande.setDateDemande(dateDemande);
                demande.setEtablissement(etablissement);
                demandeService.insert(demande);
                model.addAttribute("demandeDTO", new DemandeDTO());
                model.addAttribute("success", "Votre demande a été envoyée avec succès !");
            }else{
                model.addAttribute("erreur", "Image non trouver !");
            }
            return "redirect:/";
        }catch(Exception e){
            model.addAttribute("error", "Erreur lors de l'upload de l'image.");
            e.printStackTrace();
            return "redirect:/";
        }
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
