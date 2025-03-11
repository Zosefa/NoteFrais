package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demande_accorder")
public class DemandeAccorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_accorder")
    private Integer idDemandeAccorder;

    @Column(name = "date_validation")
    private Date dateValidation;

    @OneToOne
    @JoinColumn(name = "id_demande")
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

    public Integer getIdDemandeAccorder() {
        return idDemandeAccorder;
    }

    public void setIdDemandeAccorder(Integer idDemandeAccorder) {
        this.idDemandeAccorder = idDemandeAccorder;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date date) {
        this.dateValidation = date;
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
