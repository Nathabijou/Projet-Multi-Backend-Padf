package com.natha.dev.Dao;

import com.natha.dev.Model.SectionCommunale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SectionCommunaleDao extends JpaRepository<SectionCommunale, Long> {
    @Query("SELECT DISTINCT sc FROM Projet p JOIN p.quartier q JOIN q.sectionCommunale sc WHERE sc.commune.id = :communeId")
    List<SectionCommunale> findByCommuneIdWithProjet(@Param("communeId") Long communeId);
}
