package com.natha.dev.Dao;

import com.natha.dev.Model.EtatAvancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EtatAvancementDao extends JpaRepository<EtatAvancement, String> {
    @Query("SELECT e FROM EtatAvancement e WHERE e.projet.idProjet = :projetId")
    List<EtatAvancement> findByProjetId(@Param("projetId") String projetId);
}
