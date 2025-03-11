package com.example.projet.service;

import com.example.projet.model.DemandeFonctionnaireRefuser;
import com.example.projet.repository.DemandeFonctionnaireRefuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeFonctionnaireRefuserService {
    @Autowired
    private DemandeFonctionnaireRefuserRepository demandeFonctionnaireRefuserRepository;

    public void create(DemandeFonctionnaireRefuser demandeFonctionnaireRefuser){
        demandeFonctionnaireRefuserRepository.save(demandeFonctionnaireRefuser);
    }

    public DemandeFonctionnaireRefuser findById(Integer id){
        return demandeFonctionnaireRefuserRepository.findById(id).get();
    }

    public List<DemandeFonctionnaireRefuser> findAll(){
        return demandeFonctionnaireRefuserRepository.findAll();
    }
}
