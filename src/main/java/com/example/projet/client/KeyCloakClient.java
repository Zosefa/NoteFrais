package com.example.projet.client;

import com.example.projet.DTO.CreateUserBody;
import com.example.projet.DTO.RoleRequest;
import com.example.projet.DTO.UpdatePasswordBody;
import com.example.projet.DTO.UpdateUserBody;
import com.example.projet.DTO.response.RoleBody;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class KeyCloakClient {
    private final WebClient webClient;

    String clientId = "security-client";
    String username = "new_user";
    String password = "password123";

    public KeyCloakClient(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public KeyClokResponseToken getToken() {
        return this.webClient.post()
                .uri("/realms/security-realm/protocol/openid-connect/token")
                .contentType(org.springframework.http.MediaType.valueOf(String.valueOf(MediaType.APPLICATION_FORM_URLENCODED)))
                .bodyValue(
                        "client_id=" + clientId +
                        "&username=" +username +
                        "&password=" +password +
                        "&grant_type=" + "password")
                .retrieve()
                .bodyToMono(KeyClokResponseToken.class)
                .block();
    }

    public UserResponse getUser(String id, String token){
        return webClient.get()
                .uri("/admin/realms/security-realm/users/"+id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .block();
    }

    public Object createUser(CreateUserBody createUserBody, String token){
        return webClient.post()
                .uri("/admin/realms/security-realm/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .bodyValue(createUserBody)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public List<UserResponse> getByUsername(String username, String token){
        return webClient.get()
                .uri("/admin/realms/security-realm/users?username="+username)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserResponse>>() {})
                .block();
    }

    public RoleBody getRoleByName(String name, String token){
        return webClient.get()
                .uri("/admin/realms/security-realm/roles/"+name)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .retrieve()
                .bodyToMono(RoleBody.class)
                .block();
    }

    public Object roleMapping(String idUser, String Token , List<RoleRequest> roleBody){
        return webClient.post()
                .uri("/admin/realms/security-realm/users/" +idUser +"/role-mappings/realm")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+Token)
                .bodyValue(roleBody)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object updateUser(String idUser, String Token , UpdateUserBody updateUserBody){
        return webClient.put()
                .uri("/admin/realms/security-realm/users/"+idUser)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+Token)
                .bodyValue(updateUserBody)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    public Object updatePassword(String idUser, String Token , UpdatePasswordBody updatePasswordBody){
        return webClient.put()
                .uri("/admin/realms/security-realm/users/"+idUser+"/reset-password")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+Token)
                .bodyValue(updatePasswordBody)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
