package com.example.projet.repository;

import com.example.projet.model.Indemnite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndemniteRepository extends JpaRepository<Indemnite, Integer> {
}
