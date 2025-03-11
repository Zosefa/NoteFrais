package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "fonctionnaire")
public class Fonctionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fonctionnaire")
    private Integer idFonctionnaire;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "dateNaissance")
    private Date dateNaissance;

    @Column(name = "CIN")
    private String CIN;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "image")
    private String image;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_poste")
    private Poste poste;

    @Column(name = "id_client")
    private Integer idClient;

    @Column(name = "numero_visa")
    private String numeroVisa;

    public Integer getIdFonctionnaire() {
        return idFonctionnaire;
    }

    public void setIdFonctionnaire(Integer idFonctionnaire) {
        this.idFonctionnaire = idFonctionnaire;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNumeroVisa() {
        return numeroVisa;
    }

    public void setNumeroVisa(String numeroVisa) {
        this.numeroVisa = numeroVisa;
    }
}
