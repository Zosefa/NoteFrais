package com.example.back.repository;

import com.example.back.model.DemandeAccorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeAccorderRepository extends JpaRepository<DemandeAccorder, Integer> {
}
