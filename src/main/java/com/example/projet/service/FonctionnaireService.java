package com.example.projet.service;

import com.example.projet.model.Fonctionnaire;
import com.example.projet.repository.FonctionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionnaireService {
    @Autowired
    private FonctionnaireRepository fonctionnaireRepository;

    public void create(Fonctionnaire fonctionnaire){ fonctionnaireRepository.save(fonctionnaire); }

    public Fonctionnaire findById(Integer id){ return fonctionnaireRepository.findById(id).get(); }

    public List<Fonctionnaire> findAll(){ return fonctionnaireRepository.findAll(); }

    public Fonctionnaire findByEmail(String email){ return fonctionnaireRepository.findByEmail(email); }
}
