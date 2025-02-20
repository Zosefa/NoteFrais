package com.example.back.service;

import com.example.back.model.Demande;
import com.example.back.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {
    @Autowired
    private DemandeRepository demandeRepository;

    public void insert(Demande demande){
        demandeRepository.save(demande);
    }

    public List<Demande> findDemandeNonSoumise(){return demandeRepository.findDemandeNotSoumis();}

    public Demande findById(Integer id){
        return demandeRepository.findById(id).get();
    }

}
