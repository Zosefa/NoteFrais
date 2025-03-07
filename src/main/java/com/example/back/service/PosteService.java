package com.example.back.service;

import com.example.back.model.Poste;
import com.example.back.repository.PosteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosteService {
    @Autowired
    private PosteRepository posteRepository;

    public void create(Poste poste){
        posteRepository.save(poste);
    }
    public Poste findById(Integer id){
        return posteRepository.findById(id).get();
    }
    public List<Poste> findAll(){
        return posteRepository.findAll();
    }

}
