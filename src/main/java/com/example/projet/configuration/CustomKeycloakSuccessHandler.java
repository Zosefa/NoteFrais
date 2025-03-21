package com.example.projet.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class CustomKeycloakSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        String redirectUrl = "/";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("roles: " + authorities);

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if (role.equals("ROLE_ADMIN")) {
                redirectUrl = "/dashboard";
                break;
            } else if (role.equals("ROLE_GESTIONNAIRE")) {
                redirectUrl = "/gestionnaire";
                break;
            } else if (role.equals("ROLE_USER")) {
                redirectUrl = "/client";
                break;
            } else if (role.equals("ROLE_SUPERADMIN")) {
                redirectUrl = "/eqima";
                break;
            }
        }

        // Redirection
        response.sendRedirect(redirectUrl);
    }
}
