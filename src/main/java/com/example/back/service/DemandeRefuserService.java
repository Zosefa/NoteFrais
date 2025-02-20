package com.example.back.service;

import com.example.back.model.DemandeRefuser;
import com.example.back.repository.DemandeRefuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeRefuserService {
    @Autowired
    private DemandeRefuserRepository demandeRefuserRepository;

    public void insert(DemandeRefuser demandeRefuser){
        demandeRefuserRepository.save(demandeRefuser);
    }

    public List<DemandeRefuser> findAll(){
        return demandeRefuserRepository.findAll();
    }

    public DemandeRefuser findById(Integer id){
        return demandeRefuserRepository.findById(id).get();
    }
}
