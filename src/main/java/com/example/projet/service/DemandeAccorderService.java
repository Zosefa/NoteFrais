package com.example.projet.service;

import com.example.projet.model.DemandeAccorder;
import com.example.projet.repository.DemandeAccorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeAccorderService {
    @Autowired
    private DemandeAccorderRepository demandeAccorderRepository;

    public void insert(DemandeAccorder demandeAccorder){
        demandeAccorderRepository.save(demandeAccorder);
    }

    public DemandeAccorder findByid(Integer id){
        return demandeAccorderRepository.findById(id).get();
    }

    public List<DemandeAccorder> findAll(){
        return demandeAccorderRepository.findAll();
    }

    public List<DemandeAccorder> findAllByEtablissement(Integer id)
    {
        return demandeAccorderRepository.findAllByEtablissement(id);
    }
}
