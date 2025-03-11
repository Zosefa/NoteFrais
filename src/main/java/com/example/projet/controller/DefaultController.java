package com.example.projet.controller;

import com.example.projet.DTO.DemandeDTO;
import com.example.projet.model.NotDataBase.UserCredentials;
import com.example.projet.service.KeyCloakService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class DefaultController {
    @Autowired
    KeyCloakService keyCloackService;

    @PostMapping("/token")
    @CrossOrigin
    public ResponseEntity<?> getTokenUsingCredentials(@RequestBody UserCredentials userCredentials) {

        String responseToken = null;
        try {

            responseToken = keyCloackService.getToken(userCredentials);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseToken, HttpStatus.OK);
    }

    @Autowired
    private KeycloakDeployment keycloakDeployment;

    @PostMapping("/logout")
    @CrossOrigin
    public RedirectView logout(HttpServletRequest request) throws ServletException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        KeycloakSecurityContext session = (KeycloakSecurityContext) token.getCredentials();

        if (session instanceof RefreshableKeycloakSecurityContext) {
            RefreshableKeycloakSecurityContext refreshableSession = (RefreshableKeycloakSecurityContext) session;
            refreshableSession.logout(keycloakDeployment);
        }
        request.logout();

        return new RedirectView("/");
    }


    @GetMapping("/username")
    public String getUsername() {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        return session.getToken().getPreferredUsername();
    }
}
