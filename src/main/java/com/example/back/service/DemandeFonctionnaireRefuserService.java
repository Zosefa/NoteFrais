package com.example.back.service;

import com.example.back.model.DemandeFonctionnaireRefuser;
import com.example.back.model.DemandeFonctionnaireValider;
import com.example.back.repository.DemandeFonctionnaireRefuserRepository;
import com.example.back.repository.DemandeFonctionnaireValiderRepository;
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
