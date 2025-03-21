package com.example.projet.repository;

import com.example.projet.model.DemandeAccorder;
import com.example.projet.model.DemandeRefuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRefuserRepository extends JpaRepository<DemandeRefuser, Integer> {
    @Query(value = """
    select * from demande_refuser dr
    left join demande d ON d.id_demande = dr.id_demande
    where d.id_etablissement = :id
""", nativeQuery = true)
    public List<DemandeRefuser> findAllByEtablissement(@Param("id") Integer id);
}
