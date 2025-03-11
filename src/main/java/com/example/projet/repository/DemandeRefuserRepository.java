package com.example.projet.repository;

import com.example.projet.model.DemandeRefuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRefuserRepository extends JpaRepository<DemandeRefuser, Integer> {
}
