package com.example.back.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/dashboard");
        } else if(authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_GESTIONNAIRE"))) {
            response.sendRedirect("/gestionnaire");
        } else {
            response.sendRedirect("/client");
        }
    }
}
