package com.example.projet.service;

import com.example.projet.model.Poste;
import com.example.projet.repository.PosteRepository;
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

    public List<Poste> findAllByEtablissement(Integer id)
    {
        return posteRepository.findAllByEtablissement(id);
    }

    public void update(Poste poste){
        posteRepository.save(poste);
    }
    public void delete(Integer id){
        posteRepository.deleteById(id);
    }
}
