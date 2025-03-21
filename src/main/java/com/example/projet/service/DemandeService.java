package com.example.projet.service;

import com.example.projet.model.Demande;
import com.example.projet.repository.DemandeRepository;
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

    public List<Demande> findDemandeNonSoumise(Integer id){return demandeRepository.findDemandeNotSoumis(id);}

    public Demande findById(Integer id){
        return demandeRepository.findById(id).get();
    }

}
