package com.example.projet.DTO;

import javax.validation.constraints.NotEmpty;

public class TypeMissionDTO {
    @NotEmpty
    private String typeMission;

    public String getTypeMission() {
        return typeMission;
    }

    public void setTypeMission(String typeMission) {
        this.typeMission = typeMission;
    }
}
