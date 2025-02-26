package com.example.back.service;

import com.example.back.model.Notification;
import com.example.back.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void create(Notification notification){
        notificationRepository.save(notification);
    }

    public Notification findById(Integer id){
        return notificationRepository.findById(id).get();
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    public List<Notification> allNotification(Integer id){
        return notificationRepository.allNotification(id);
    }

    public Integer total(Integer id){
        return notificationRepository.countNotif(id);
    }
}
