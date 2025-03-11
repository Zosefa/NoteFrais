package com.example.projet.repository;

import com.example.projet.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = """
        SELECT n.id_notifictaion, n.etat, n.id_demande_fonctionnaire, n.id_fonctionnaire FROM notification n
         LEFT JOIN notification_lue nl ON n.id_notifictaion = nl.id_notification
         WHERE n.id_notifictaion NOT IN(SELECT id_notification from notification_lue)
         AND n.id_fonctionnaire = :id
    """, nativeQuery = true)
    public List<Notification> allNotification(@Param("id") Integer id);

    @Query(value = """
         SELECT count(n.id_notifictaion) as total from notification n
         LEFT JOIN notification_lue nl ON n.id_notifictaion = nl.id_notification
         WHERE n.id_notifictaion NOT IN(SELECT id_notification from notification_lue)
         AND n.id_fonctionnaire = :id
    """, nativeQuery = true)
    public Integer countNotif(@Param("id") Integer id);

}
