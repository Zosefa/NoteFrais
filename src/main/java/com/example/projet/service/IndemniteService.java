package com.example.projet.service;

import com.example.projet.model.Indemnite;
import com.example.projet.repository.IndemniteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndemniteService {
    @Autowired
    private IndemniteRepository indemniteRepository;

    public void create(Indemnite indemnite){
        indemniteRepository.save(indemnite);
    }
    public List<Indemnite> findAll(){
        return indemniteRepository.findAll();
    }
    public Indemnite findById(Integer id){
        return indemniteRepository.findById(id).get();
    }

    public List<Indemnite> findAllByEtablissement(Integer id){
        return indemniteRepository.findAllByEtablissement(id);
    }
}
