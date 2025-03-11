package com.example.projet.controller;

import com.example.projet.DTO.TransfersBody;
import com.example.projet.DTO.response.SavingsAccountResponse;
import com.example.projet.client.EqimaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {

    @Autowired
    private EqimaClient eqimaClient;

    @GetMapping
    public Object test(){
        SavingsAccountResponse result = eqimaClient.accountBalance(73);

        Integer id = result.getSavingsAccounts()[0].getId();

//        TransfersBody transfersBody = new TransfersBody();
//        transfersBody.setTransferAmount("300");
//        transfersBody.setToAccountId(35);
//
//        eqimaClient.transfert(transfersBody);

        return id;
    }
}
