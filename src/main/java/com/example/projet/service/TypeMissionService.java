package com.example.projet.service;

import com.example.projet.model.TypeMission;
import com.example.projet.repository.TypeMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeMissionService {
    @Autowired
    private TypeMissionRepository typeMissionRepository;

    public void create(TypeMission typeMission){
        typeMissionRepository.save(typeMission);
    }

    public List<TypeMission> findAll(){
        return typeMissionRepository.findAll();
    }

    public TypeMission findById(Integer id){
        return typeMissionRepository.findById(id).get();
    }
}
