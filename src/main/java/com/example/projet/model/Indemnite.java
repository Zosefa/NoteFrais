package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "indemnite")
public class Indemnite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_indemnite")
    private Integer idIndemnite;

    @Column(name = "indemnite")
    private Float indemnite;

    @ManyToOne
    @JoinColumn(name = "id_poste")
    private Poste poste;

    public Integer getIdIndemnite() {
        return idIndemnite;
    }

    public void setIdIndemnite(Integer idIndemnite) {
        this.idIndemnite = idIndemnite;
    }

    public Float getIndemnite() {
        return indemnite;
    }

    public void setIndemnite(Float indemnite) {
        this.indemnite = indemnite;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }
}
