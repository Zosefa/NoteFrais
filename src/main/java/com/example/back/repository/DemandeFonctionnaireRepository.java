package com.example.back.repository;

import com.example.back.model.Demande;
import com.example.back.model.DemandeFonctionnaire;
import com.example.back.model.ResultDTO.ResultDemandeFonctionnaireDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeFonctionnaireRepository extends JpaRepository<DemandeFonctionnaire,Integer> {

    @Query(value = """
    select * from demande_fonctionnaire d 
    WHERE d.id_demande_fonctionnaire NOT IN (
        SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_valider
    )
    AND d.id_demande_fonctionnaire NOT IN (
        SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_refuser
    )
""",nativeQuery = true)
    public List<DemandeFonctionnaire> findDemandeNotSoumis();

//    @Query(value = """
//        SELECT
//                    df.id_demande_fonctionnaire AS idDemandeFonctionnaire,
//                    df.date_demande AS dateDemande,
//                    df.date_debut_mission AS dateDebutMission,
//                    df.date_fin_mission AS dateFinMission,
//                    df.duree,
//                    df.motif,
//                    df.document,
//                    DATEDIFF(df.date_debut_mission, CURDATE()) AS joursRestants,
//                    f.nom AS fonctionnaireNom,
//                    f.prenom AS fonctionnairePrenom
//                FROM
//                    demande_fonctionnaire df
//                JOIN
//                    fonctionnaire f ON df.id_fonctionnaire = f.id_fonctionnaire
//                WHERE
//                    df.date_debut_mission >= CURDATE()
//                AND
//                    f.id_fonctionnaire <> :id
//                AND df.id_demande_fonctionnaire NOT IN(
//                    SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_valider
//                )
//                AND df.id_demande_fonctionnaire NOT IN(
//                    SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_refuser
//                )
//                ORDER BY
//                    joursRestants ASC
//""", nativeQuery = true)
//    public List<ResultDemandeFonctionnaireDTO> findDemandeFonctionnaire(@Param("id") Integer id);



    @Query(value = """
        SELECT
                    df.id_demande_fonctionnaire AS idDemandeFonctionnaire,
                    df.date_demande AS dateDemande,
                    df.date_debut_mission AS dateDebutMission,
                    df.date_fin_mission AS dateFinMission,
                    df.duree,
                    df.motif,
                    df.document,
                    DATEDIFF(df.date_debut_mission, CURDATE()) AS joursRestants,
                    f.nom AS fonctionnaireNom,
                    f.prenom AS fonctionnairePrenom,
                    CAST(i.indemnite AS FLOAT) AS indemnite,
                    CAST(df.duree * i.indemnite AS FLOAT) AS totalIndemnite
                FROM
                    demande_fonctionnaire df
                JOIN
                    fonctionnaire f ON df.id_fonctionnaire = f.id_fonctionnaire
                JOIN
                    poste p ON p.id_poste = f.id_poste
                JOIN
                    indemnite i ON i.id_poste = p.id_poste
                WHERE
                    df.date_debut_mission >= CURDATE()
                AND
                    f.id_fonctionnaire <> :id
                AND df.id_demande_fonctionnaire NOT IN(
                    SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_valider
                )
                AND df.id_demande_fonctionnaire NOT IN(
                    SELECT id_demande_fonctionnaire FROM demande_fonctionnaire_refuser
                )
                ORDER BY
                    joursRestants ASC
""", nativeQuery = true)
    public List<ResultDemandeFonctionnaireDTO> findDemandeFonctionnaire(@Param("id") Integer id);
}
