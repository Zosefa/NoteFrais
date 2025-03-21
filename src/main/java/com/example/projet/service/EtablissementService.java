package com.example.projet.service;

import com.example.projet.model.Etablissement;
import com.example.projet.repository.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtablissementService {
    @Autowired
    private EtablissementRepository etablissementRepository;

    public void create(Etablissement etablissement){
        etablissementRepository.save(etablissement);
    }

    public List<Etablissement> findAll(){
        return etablissementRepository.findAll();
    }

    public Etablissement findById(Integer id){
        return etablissementRepository.findById(id).get();
    }

    public void update(Etablissement etablissement){
        etablissementRepository.save(etablissement);
    }

    public void delete(Integer id){
        etablissementRepository.deleteById(id);
    }
}
