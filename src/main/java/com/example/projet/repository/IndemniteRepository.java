package com.example.projet.repository;

import com.example.projet.model.Indemnite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndemniteRepository extends JpaRepository<Indemnite, Integer> {

    @Query(value = """
    select * from indemnite i
     left join poste p on p.id_poste = i.id_poste
     where p.id_etablissement = :id
""", nativeQuery = true)
    public List<Indemnite> findAllByEtablissement(@Param("id") Integer id);
}
