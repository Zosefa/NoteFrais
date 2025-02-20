package com.example.back.client;

import com.example.back.model.DTO.CreateClientDTO;
import com.example.back.model.DTO.SavingsAccountResponse;
import com.example.back.model.ResultDTO.ClientResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EqimaClient {
    private final WebClient webClient;
    String username = "mifos";
    String password = "password";
    String authHeader = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());


    public EqimaClient(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://fineract.cb.eqima.org").build();
    }

    public ClientResponseDTO createClient(CreateClientDTO body){
        return webClient.post()
                .uri("/fineract-provider/api/v1/clients")
                .header(HttpHeaders.AUTHORIZATION, "Basic "+authHeader)
                .header("Fineract-Platform-TenantId", "default")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ClientResponseDTO.class)
                .block();
    }

    public SavingsAccountResponse accountBalance(Integer id){
        return webClient.get()
                .uri("fineract-provider/api/v1/clients/"+id+"/accounts")
                .header(HttpHeaders.AUTHORIZATION, "Basic "+authHeader)
                .header("Fineract-Platform-TenantId", "default")
                .retrieve()
                .bodyToMono(SavingsAccountResponse.class)
                .block();
    }
}
