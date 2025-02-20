package com.example.back.service;

import com.example.back.model.DemandeFonctionnaire;
import com.example.back.model.ResultDTO.ResultDemandeFonctionnaireDTO;
import com.example.back.repository.DemandeFonctionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeFonctionnaireService {
    @Autowired
    private DemandeFonctionnaireRepository demandeFonctionnaireRepository;

    public void create(DemandeFonctionnaire demandeFonctionnaire){
        demandeFonctionnaireRepository.save(demandeFonctionnaire);
    }

    public DemandeFonctionnaire findById(Integer id){
        return demandeFonctionnaireRepository.findById(id).get();
    }

    public List<DemandeFonctionnaire> findAll(){
        return demandeFonctionnaireRepository.findAll();
    }

    public List<DemandeFonctionnaire> demandeNonSoumis(){
        return demandeFonctionnaireRepository.findDemandeNotSoumis();
    }

    public List<ResultDemandeFonctionnaireDTO> findDemandeFonctionnaire(Integer id){
        return demandeFonctionnaireRepository.findDemandeFonctionnaire(id);
    }
}
