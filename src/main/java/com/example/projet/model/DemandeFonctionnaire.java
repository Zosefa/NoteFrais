package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demande_fonctionnaire")
public class DemandeFonctionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_fonctionnaire")
    private Integer idDemandeFonctionnaire;

    @Column(name = "date_demande")
    private Date dateDemande;

    @Column(name = "date_debut_mission")
    private Date dateDebutMission;

    @Column(name = "date_fin_mission")
    private Date dateFinMission;

    @Column(name = "duree")
    private Integer duree;

    @ManyToOne
    @JoinColumn(name = "id_fonctionnaire")
    private Fonctionnaire fonctionnaire;

    @ManyToOne
    @JoinColumn(name = "id_type_mission")
    private TypeMission typeMission;

    @Column(name = "motif")
    private String motif;

    @Column(name = "document")
    private String document;

    @Transient
    private Date joursRestants;

    public Integer getIdDemandeFonctionnaire() {
        return idDemandeFonctionnaire;
    }

    public void setIdDemandeFonctionnaire(Integer idDemandeFonctionnaire) {
        this.idDemandeFonctionnaire = idDemandeFonctionnaire;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Fonctionnaire getFonctionnaire() {
        return fonctionnaire;
    }

    public void setFonctionnaire(Fonctionnaire fonctionnaire) {
        this.fonctionnaire = fonctionnaire;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Date getDateDebutMission() {
        return dateDebutMission;
    }

    public void setDateDebutMission(Date dateDebutMission) {
        this.dateDebutMission = dateDebutMission;
    }

    public Date getDateFinMission() {
        return dateFinMission;
    }

    public void setDateFinMission(Date dateFinMission) {
        this.dateFinMission = dateFinMission;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Date getJoursRestants() {
        return joursRestants;
    }

    public void setJoursRestants(Date joursRestants) {
        this.joursRestants = joursRestants;
    }

    public TypeMission getTypeMission() {
        return typeMission;
    }

    public void setTypeMission(TypeMission typeMission) {
        this.typeMission = typeMission;
    }
}
