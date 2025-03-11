package com.example.projet.service;

import com.example.projet.model.DemandeFonctionnaireValider;
import com.example.projet.repository.DemandeFonctionnaireValiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeFonctionnaireValiderService {
    @Autowired
    private DemandeFonctionnaireValiderRepository demandeFonctionnaireValiderRepository;

    public void create(DemandeFonctionnaireValider demandeFonctionnaireValider){
        demandeFonctionnaireValiderRepository.save(demandeFonctionnaireValider);
    }

    public DemandeFonctionnaireValider findById(Integer id){
        return demandeFonctionnaireValiderRepository.findById(id).get();
    }

    public List<DemandeFonctionnaireValider> findAll(){
        return demandeFonctionnaireValiderRepository.findAll();
    }
}
