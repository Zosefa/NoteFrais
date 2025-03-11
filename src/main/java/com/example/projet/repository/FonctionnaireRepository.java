package com.example.projet.repository;

import com.example.projet.model.Fonctionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonctionnaireRepository extends JpaRepository<Fonctionnaire, Integer> {
    Fonctionnaire findByEmail(String email);
}
