package com.example.back.configuration;

import com.example.back.component.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/inscription/**").permitAll()
                        .requestMatchers("/envoie").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/Uploads/**").permitAll()
                        .requestMatchers("/Toast/***").permitAll()
                        .requestMatchers("/Tailwind/***").permitAll()
                        .requestMatchers("/dist/**").permitAll()
                        .requestMatchers("/bootstrap/**").permitAll()
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/dashboard/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/client/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/gestionnaire/**").hasAuthority("ROLE_GESTIONNAIRE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                )
                .logout(config -> config.logoutSuccessUrl("/login"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
