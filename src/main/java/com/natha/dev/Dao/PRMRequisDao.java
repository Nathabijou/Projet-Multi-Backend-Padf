package com.natha.dev.Dao;

import com.natha.dev.Model.PRMRequis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PRMRequisDao extends JpaRepository<PRMRequis, Long> {
    @Query("SELECT r FROM PRMRequis r WHERE r.projet.idProjet = :projetId AND r.quartier.id = :quartierId")
    List<PRMRequis> findByProjetIdAndQuartierId(@Param("projetId") String projetId, @Param("quartierId") Long quartierId);
}
