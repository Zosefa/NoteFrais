package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "type_mission")
public class TypeMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_mission")
    private Integer idTypeMission;

    @Column(name = "type_mission")
    private String TypeMission;

    public Integer getIdTypeMission() {
        return idTypeMission;
    }

    public void setIdTypeMission(Integer idTypeMission) {
        this.idTypeMission = idTypeMission;
    }

    public String getTypeMission() {
        return TypeMission;
    }

    public void setTypeMission(String typeMission) {
        TypeMission = typeMission;
    }
}
