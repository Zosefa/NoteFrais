package com.example.projet.repository;

import com.example.projet.model.TypeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMissionRepository extends JpaRepository<TypeMission, Integer> {
}
