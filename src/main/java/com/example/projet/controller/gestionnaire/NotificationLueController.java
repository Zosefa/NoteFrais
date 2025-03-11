package com.example.projet.controller.gestionnaire;

import com.example.projet.model.Notification;
import com.example.projet.model.NotificationLue;
import com.example.projet.service.NotificationLueService;
import com.example.projet.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class NotificationLueController {
    @Autowired
    private NotificationLueService notificationLueService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notificationLue/{id}")
    public String create(@PathVariable("id") Integer id,
                         @RequestParam(value = "previousUrl", required = false) String previousUrl,
                         RedirectAttributes redirectAttributes) {
        // Vérifier si la notification existe
        Notification notification = notificationService.findById(id);
        if (notification == null) {
            redirectAttributes.addFlashAttribute("error", "Notification introuvable.");
            return "redirect:" + (previousUrl != null ? previousUrl : "/dashboard");
        }

        // Marquer la notification comme lue
        NotificationLue notificationLue = new NotificationLue();
        notificationLue.setNotification(notification);
        notificationLueService.create(notificationLue);

        // Rediriger vers l'ancienne page ou dashboard par défaut
        return "redirect:" + (previousUrl != null ? previousUrl : "/dashboard");
    }


}
