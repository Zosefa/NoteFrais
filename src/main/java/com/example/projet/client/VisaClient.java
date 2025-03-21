package com.example.projet.client;

import com.example.projet.DTO.VisaDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class VisaClient {
    private final WebClient webClient;

    public VisaClient(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://192.168.88.69:8080").build();
    }

    public String visa(VisaDTO visaDTO){
        return webClient.post()
                .uri("/cybersource/pay")
                .header("Cntent-Type", "application/json")
                .bodyValue(visaDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
