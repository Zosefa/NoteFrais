package com.example.back.service;

import com.example.back.model.NotificationLue;
import com.example.back.repository.NotificationLueRepository;
import com.example.back.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationLueService {
    @Autowired
    private NotificationLueRepository notificationLueRepository;

    public void create(NotificationLue notificationLue){
        notificationLueRepository.save(notificationLue);
    }

    public List<NotificationLue> findAll(){
        return notificationLueRepository.findAll();
    }

    public NotificationLue findById(Integer id){
        return notificationLueRepository.findById(id).get();
    }
}
