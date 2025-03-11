package com.example.projet.DTO.response;

import java.sql.Date;

public interface ResultDemandeFonctionnaireDTO {
    Integer getIdDemandeFonctionnaire();
    Date getDateDemande();
    Date getDateDebutMission();
    Date getDateFinMission();
    Integer getDuree();
    String getMotif();
    String getDocument();
    Integer getJoursRestants();
    String getFonctionnaireNom();
    String getFonctionnairePrenom();
    Float getIndemnite();
    Float getTotalIndemnite();
}
