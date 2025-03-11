package com.example.projet.client;

import com.example.projet.DTO.CreateClientDTO;
import com.example.projet.DTO.TransfersBody;
import com.example.projet.DTO.response.ClientResponseDTO;
import com.example.projet.DTO.response.SavingsAccountResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .headers(headers -> headers.setBasicAuth(username, password))
                .header("Fineract-Platform-TenantId", "default")
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), response ->
                        response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.out.println("Erreur API: " + errorBody);
                            return Mono.error(new RuntimeException("Erreur API: " + errorBody));
                        }))
                .bodyToMono(ClientResponseDTO.class)
                .block();
    }

    public SavingsAccountResponse accountBalance(Integer id){
        return webClient.get()
                .uri("/fineract-provider/api/v1/clients/"+id+"/accounts")
                .header(HttpHeaders.AUTHORIZATION, "Basic "+authHeader)
                .header("Fineract-Platform-TenantId", "default")
                .retrieve()
                .bodyToMono(SavingsAccountResponse.class)
                .block();
    }

    public Object transfert(TransfersBody body){
        return webClient.post()
                .uri("/fineract-provider/api/v1/accounttransfers")
                .header(HttpHeaders.AUTHORIZATION, "Basic "+authHeader)
                .header("Fineract-Platform-TenantId", "default")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
