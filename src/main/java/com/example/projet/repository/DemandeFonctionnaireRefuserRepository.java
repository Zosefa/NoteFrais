package com.example.projet.repository;

import com.example.projet.model.DemandeFonctionnaireRefuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeFonctionnaireRefuserRepository extends JpaRepository<DemandeFonctionnaireRefuser, Integer> {
}
