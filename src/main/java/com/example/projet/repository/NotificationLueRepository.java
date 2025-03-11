package com.example.projet.repository;

import com.example.projet.model.NotificationLue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLueRepository extends JpaRepository<NotificationLue, Integer> {
}
