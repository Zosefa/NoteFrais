package com.example.projet.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "demande_fonctionnaire_valider")
public class DemandeFonctionnaireValider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande_fonctionnaire_valider")
    private Integer idDemandeFonctionnaireValider;

    @Column(name = "date_validation")
    private Date dateValidation;

    @OneToOne
    @JoinColumn(name = "id_demande_fonctionnaire")
    private DemandeFonctionnaire demandeFonctionnaire;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

    public Integer getIdDemandeFonctionnaireValider() {
        return idDemandeFonctionnaireValider;
    }

    public void setIdDemandeFonctionnaireValider(Integer idDemandeFonctionnaireValider) {
        this.idDemandeFonctionnaireValider = idDemandeFonctionnaireValider;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public DemandeFonctionnaire getDemandeFonctionnaire() {
        return demandeFonctionnaire;
    }

    public void setDemandeFonctionnaire(DemandeFonctionnaire demandeFonctionnaire) {
        this.demandeFonctionnaire = demandeFonctionnaire;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
