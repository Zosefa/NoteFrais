package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Integer idDemande;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "tel")
    private String tel;

    @Column(name = "CIN")
    private String CIN;

    @Column(name = "date_naissance")
    private Date dateNaissance;

    @Column(name = "image")
    private String image;

    @Column(name = "imageCIN")
    private String imageCIN;

    @Column(name = "email")
    private String email;

    @Column(name = "date_demande")
    private Date dateDemande;

    public Integer getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Integer idDemande) {
        this.idDemande = idDemande;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageCIN() {
        return imageCIN;
    }

    public void setImageCIN(String imageCIN) {
        this.imageCIN = imageCIN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }
}
