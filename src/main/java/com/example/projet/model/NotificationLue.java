package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "notificationLue")
public class NotificationLue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification_lue")
    private Integer idNotificationLue;

    @OneToOne
    @JoinColumn(name = "id_notification")
    private Notification notification;

    public Integer getIdNotificationLue() {
        return idNotificationLue;
    }

    public void setIdNotificationLue(Integer idNotificationLue) {
        this.idNotificationLue = idNotificationLue;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
