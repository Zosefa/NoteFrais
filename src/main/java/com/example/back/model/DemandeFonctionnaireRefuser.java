package com.example.back.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "demande_fonctionnaire_Refuser")
public class DemandeFonctionnaireRefuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_fonctionnaire_refuser")
    private Integer idDemandeFonctionnaireRefuser;

    @Column(name = "date_refus")
    private Date dateRefus;

    @OneToOne
    @JoinColumn(name = "id_demande_fonctionnaire")
    private DemandeFonctionnaire demandeFonctionnaire;

    public Integer getIdDemandeFonctionnaireRefuser() {
        return idDemandeFonctionnaireRefuser;
    }

    public void setIdDemandeFonctionnaireRefuser(Integer idDemandeFonctionnaireRefuser) {
        this.idDemandeFonctionnaireRefuser = idDemandeFonctionnaireRefuser;
    }

    public Date getDateRefus() {
        return dateRefus;
    }

    public void setDateRefus(Date dateRefus) {
        this.dateRefus = dateRefus;
    }

    public DemandeFonctionnaire getDemandeFonctionnaire() {
        return demandeFonctionnaire;
    }

    public void setDemandeFonctionnaire(DemandeFonctionnaire demandeFonctionnaire) {
        this.demandeFonctionnaire = demandeFonctionnaire;
    }
}
