package com.example.back.repository;

import com.example.back.model.DemandeFonctionnaireRefuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeFonctionnaireRefuserRepository extends JpaRepository<DemandeFonctionnaireRefuser, Integer> {
}
