package com.example.projet.repository;

import com.example.projet.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    public Users findByEmail(String Email);
}
