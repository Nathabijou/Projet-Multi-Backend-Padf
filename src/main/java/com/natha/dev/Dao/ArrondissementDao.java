package com.natha.dev.Dao;

import com.natha.dev.Model.Arrondissement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArrondissementDao extends JpaRepository<Arrondissement, Long> {
    List<Arrondissement> findByDepartementId(Long id);

    @Query("SELECT DISTINCT p.quartier.sectionCommunale.commune.arrondissement FROM Projet p WHERE p.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId")
    List<Arrondissement> findByDepartementIdWithProjects(@Param("departementId") Long departementId);
}
