package com.example.back.repository;

import com.example.back.model.DemandeRefuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeRefuserRepository extends JpaRepository<DemandeRefuser, Integer> {
}
