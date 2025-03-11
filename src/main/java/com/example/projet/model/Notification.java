package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notifictaion")
    private Integer idNotification;

    @ManyToOne
    @JoinColumn(name = "id_demande_fonctionnaire")
    private DemandeFonctionnaire demandeFonctionnaire;

    @Column(name = "etat")
    private Boolean etat;

    @ManyToOne
    @JoinColumn(name = "id_fonctionnaire")
    private Fonctionnaire fonctionnaireValidateur;

    public Integer getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(Integer idNotification) {
        this.idNotification = idNotification;
    }

    public DemandeFonctionnaire getDemandeFonctionnaire() {
        return demandeFonctionnaire;
    }

    public void setDemandeFonctionnaire(DemandeFonctionnaire demandeFonctionnaire) {
        this.demandeFonctionnaire = demandeFonctionnaire;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Fonctionnaire getFonctionnaireValidateur() {
        return fonctionnaireValidateur;
    }

    public void setFonctionnaireValidateur(Fonctionnaire fonctionnaireValidateur) {
        this.fonctionnaireValidateur = fonctionnaireValidateur;
    }
}
