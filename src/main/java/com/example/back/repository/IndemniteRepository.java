package com.example.back.repository;

import com.example.back.model.Indemnite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndemniteRepository extends JpaRepository<Indemnite, Integer> {
}
