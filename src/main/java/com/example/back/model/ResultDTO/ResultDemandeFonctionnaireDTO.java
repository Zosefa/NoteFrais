package com.example.back.model.ResultDTO;

import com.example.back.model.Fonctionnaire;

import java.sql.Date;

public class ResultDemandeFonctionnaireDTO {
    private Integer idDemandeFonctionnaire;
    private Date dateDemande;
    private Date dateDebutMission;
    private Date dateFinMission;
    private Integer duree;
    private String motif;
    private String document;
    private Integer joursRestants;
    private String fonctionnaireNom;

    private String fonctionnairePrenom;

    private Float indemnite;

    private Float totalIndemnite;


    public ResultDemandeFonctionnaireDTO(Integer idDemandeFonctionnaire, Date dateDemande, Date dateDebutMission,
                                   Date dateFinMission, Integer duree, String motif, String document, Integer joursRestants, String nom, String prenom, Float indemnite, Float totalIndemnite) {
        this.idDemandeFonctionnaire = idDemandeFonctionnaire;
        this.dateDemande = dateDemande;
        this.dateDebutMission = dateDebutMission;
        this.dateFinMission = dateFinMission;
        this.duree = duree;
        this.motif = motif;
        this.document = document;
        this.joursRestants = joursRestants;
        this.fonctionnaireNom = nom;
        this.fonctionnairePrenom = prenom;
        this.indemnite = indemnite;
        this.totalIndemnite = totalIndemnite;
    }

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

    public Integer getJoursRestants() {
        return joursRestants;
    }

    public void setJoursRestants(Integer joursRestants) {
        this.joursRestants = joursRestants;
    }

    public String getFonctionnaireNom() {
        return fonctionnaireNom;
    }

    public void setFonctionnaireNom(String fonctionnaireNom) {
        this.fonctionnaireNom = fonctionnaireNom;
    }

    public String getFonctionnairePrenom() {
        return fonctionnairePrenom;
    }

    public void setFonctionnairePrenom(String fonctionnairePrenom) {
        this.fonctionnairePrenom = fonctionnairePrenom;
    }

    public Float getIndemnite() {
        return indemnite;
    }

    public void setIndemnite(Float indemnite) {
        this.indemnite = indemnite;
    }

    public Float getTotalIndemnite() {
        return totalIndemnite;
    }

    public void setTotalIndemnite(Float totalIndemnite) {
        this.totalIndemnite = totalIndemnite;
    }
}
