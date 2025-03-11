package com.example.projet.repository;

import com.example.projet.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Integer> {

    @Query(value = """
    select * from demande d 
    WHERE d.id_demande NOT IN (
        SELECT id_demande FROM demande_accorder
    )
    AND d.id_demande NOT IN (
        SELECT id_demande FROM demande_refuser
    )
""",nativeQuery = true)
    public List<Demande> findDemandeNotSoumis();
}
