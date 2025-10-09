package com.natha.dev.Dao;

import com.natha.dev.Model.ProcessusConsultatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProcessusConsultatifDao extends JpaRepository<ProcessusConsultatif, Long> {
    List<ProcessusConsultatif> findByCommuneId(Long communeId);
}
