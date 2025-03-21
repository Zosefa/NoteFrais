package com.example.projet.repository;

import com.example.projet.model.Demande;
import com.example.projet.model.DemandeAccorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeAccorderRepository extends JpaRepository<DemandeAccorder, Integer> {
    @Query(value = """
    select * from demande_accorder da
    left join demande d ON d.id_demande = da.id_demande
    where d.id_etablissement = :id
""", nativeQuery = true)
    public List<DemandeAccorder> findAllByEtablissement(@Param("id") Integer id);
}
