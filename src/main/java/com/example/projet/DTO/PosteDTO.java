package com.example.projet.DTO;

import javax.validation.constraints.NotEmpty;

public class PosteDTO {
    @NotEmpty
    private String poste;

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
