package com.example.projet.repository;

import com.example.projet.model.TypeMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeMissionRepository extends JpaRepository<TypeMission, Integer> {
    @Query(value = """
        select * from type_mission where id_etablissement = :id
""", nativeQuery = true)
    public List<TypeMission> findAllByEtablissement(@Param("id") Integer id);
}
