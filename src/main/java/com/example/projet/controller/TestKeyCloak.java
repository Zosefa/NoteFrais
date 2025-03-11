package com.example.projet.controller;

import com.example.projet.DTO.CreateClientDTO;
import com.example.projet.DTO.response.ClientResponseDTO;
import com.example.projet.DTO.response.KeyClokResponseToken;
import com.example.projet.DTO.response.UserResponse;
import com.example.projet.client.EqimaClient;
import com.example.projet.client.KeyCloakClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyCloak")
public class TestKeyCloak {

    @Autowired
    private KeyCloakClient keyCloakClient;

    @Autowired
    private EqimaClient eqimaClient;

    @GetMapping
    public ClientResponseDTO getToken(){
//        KeyClokResponseToken response = keyCloakClient.getToken();
//        UserResponse userSponse = keyCloakClient.getUser("1595819b-3b5c-4aab-b9dc-eb5fc11c74ae",response.getAccess_token());
//        return userSponse;
        CreateClientDTO client = new CreateClientDTO();
        client.setExternalId("0347890861");
        client.setFirstname("RAZOSEFA");
        client.setLastname("Zosefa");

        System.out.println(client);
        ClientResponseDTO resultApi = eqimaClient.createClient(client);
        return resultApi;
    }
}
