package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demande_refuser")
public class DemandeRefuser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_refuser")
    private Integer idDemandeRefuser;

    @Column(name = "date_refus")
    private Date dateRefus;

    @OneToOne
    @JoinColumn(name = "id_demande")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

    public Integer getIdDemandeRefuser() {
        return idDemandeRefuser;
    }

    public void setIdDemandeRefuser(Integer idDemandeRefuser) {
        this.idDemandeRefuser = idDemandeRefuser;
    }

    public Date getDateRefus() {
        return dateRefus;
    }

    public void setDateRefus(Date dateRefus) {
        this.dateRefus = dateRefus;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Users getUser() {
        return users;
    }

    public void setUser(Users users) {
        this.users = users;
    }
}
