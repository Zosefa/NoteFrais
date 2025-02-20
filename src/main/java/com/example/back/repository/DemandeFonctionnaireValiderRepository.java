package com.example.back.repository;

import com.example.back.model.DemandeFonctionnaireValider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeFonctionnaireValiderRepository extends JpaRepository<DemandeFonctionnaireValider,Integer> {
}
