package com.example.projet.controller.client;

import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.SavingsAccountResponse;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.EqimaClient;
import com.example.projet.client.KeyCloakClient;
import com.example.projet.model.Fonctionnaire;
import com.example.projet.model.Notification;
import com.example.projet.service.FonctionnaireService;
import com.example.projet.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/client")
public class MisaSoldeController {
    @Autowired
    private FonctionnaireService fonctionnaireService;

    @Autowired
    private EqimaClient eqimaClient;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private KeyCloakClient keyCloakClient;

    @GetMapping("/misa-solde")
    public String misaSolde(Model model, HttpServletRequest request){
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
        SavingsAccountResponse result = eqimaClient.accountBalance(fonctionnaire.getIdClient());
        Double solde = Double.valueOf(0);
        if(result != null){
            solde = result.getSavingsAccounts()[0].getAccountBalance();
        }
        List<Notification> notifications = notificationService.allNotification(fonctionnaire.getIdFonctionnaire());
        Integer total = notificationService.total(fonctionnaire.getIdFonctionnaire());
        String referer = request.getHeader("Referer");
        if (referer == null) {
            referer = "/client/misa-solde";
        }

        model.addAttribute("notifications", notifications);
        model.addAttribute("total",total);
        model.addAttribute("previousUrl", referer);
        model.addAttribute("solde", solde);
        return "client/solde";
    }
}
