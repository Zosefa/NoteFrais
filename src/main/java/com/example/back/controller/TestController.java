package com.example.back.controller;

import com.example.back.client.EqimaClient;
import com.example.back.model.DTO.CreateClientDTO;
import com.example.back.model.DTO.SavingsAccountResponse;
import com.example.back.model.DTO.TestDTO;
import com.example.back.model.ResultDTO.ClientResponseDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private EqimaClient eqimaClient;


    @PostMapping
    public Integer test(@RequestBody TestDTO test ){
        CreateClientDTO clientDTO = new CreateClientDTO();
        clientDTO.setFirstname(test.getNom());
        clientDTO.setLastname(test.getPrenom());
        clientDTO.setExternalId(test.getNumero());
        System.out.println(clientDTO.getAddress());
        ClientResponseDTO result = eqimaClient.createClient(clientDTO);

        return result.getClientId();
    }

    @GetMapping
    public double getTes(){
        SavingsAccountResponse result = eqimaClient.accountBalance(35);
        return result.getSavingsAccounts()[0].getAccountBalance();
    }

}
