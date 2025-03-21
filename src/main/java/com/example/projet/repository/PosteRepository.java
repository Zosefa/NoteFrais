package com.example.projet.repository;

import com.example.projet.model.Demande;
import com.example.projet.model.Poste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosteRepository extends JpaRepository<Poste, Integer> {
    @Query(value = """
    select * from poste where id_etablissement = :id
""", nativeQuery = true)
    public List<Poste> findAllByEtablissement(@Param("id") Integer id);
}
