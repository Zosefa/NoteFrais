package com.example.projet.DTO;

import javax.validation.constraints.NotNull;

public class IndemniteDTO {
    @NotNull
    private Float indemnite;

    @NotNull
    private Integer poste;

    public Float getIndemnite() {
        return indemnite;
    }

    public void setIndemnite(Float indemnite) {
        this.indemnite = indemnite;
    }

    public Integer getPoste() {
        return poste;
    }

    public void setPoste(Integer poste) {
        this.poste = poste;
    }
}
