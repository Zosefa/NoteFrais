package com.example.projet.controller.gestionnaire;

import com.example.projet.model.DemandeFonctionnaire;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Notification;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FonctionnaireService fonctionnaireService;

    public void insert(DemandeFonctionnaire demandeFonctionnaire, Boolean etat){
        Fonctionnaire fonctionnaire = fonctionnaireService.findById(demandeFonctionnaire.getFonctionnaire().getIdFonctionnaire());
        Notification notification = new Notification();
        notification.setEtat(etat);
        notification.setDemandeFonctionnaire(demandeFonctionnaire);
        notification.setFonctionnaireValidateur(fonctionnaire);

        notificationService.create(notification);
    }
}
