package com.example.projet.repository;

import com.example.projet.model.DemandeAccorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeAccorderRepository extends JpaRepository<DemandeAccorder, Integer> {
}
