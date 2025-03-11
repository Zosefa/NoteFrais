package com.example.projet.repository;

import com.example.projet.model.DemandeFonctionnaireValider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeFonctionnaireValiderRepository extends JpaRepository<DemandeFonctionnaireValider,Integer> {
}
