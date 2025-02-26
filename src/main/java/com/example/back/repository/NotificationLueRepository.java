package com.example.back.repository;

import com.example.back.model.NotificationLue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLueRepository extends JpaRepository<NotificationLue, Integer> {
}
