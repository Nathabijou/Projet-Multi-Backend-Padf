package com.natha.dev.Dao;

import com.natha.dev.Model.EtatAvancement;
import com.natha.dev.Model.SousEtatAvancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SousEtatAvancementDao extends JpaRepository<SousEtatAvancement, String> {
    List<SousEtatAvancement> findByEtatAvancement(EtatAvancement etatAvancement);
    
    @Query("SELECT s FROM SousEtatAvancement s WHERE s.etatAvancement.id = :etatAvancementId")
    List<SousEtatAvancement> findByEtatAvancementId(@Param("etatAvancementId") String etatAvancementId);
}
