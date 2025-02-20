package com.example.back.service;

import com.example.back.model.Indemnite;
import com.example.back.repository.IndemniteRepository;
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
}
