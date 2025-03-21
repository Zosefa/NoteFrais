package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "etablissement")
public class Etablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etablissement")
    private Integer idEtablissement;

    @Column(name = "etablissement")
    private String etablissement;

    public Integer getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(Integer idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }
}
