package com.natha.dev.Dao;

import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjetDao extends JpaRepository<Projet, String> {
    List<Projet> findByComposanteId(Long composanteId);
    List<Projet> findByComposanteIdAndQuartierId(Long composanteId, String quartierId);
    List<Projet> findAllByActiveTrue();

    Optional<Projet> findById(String idProjet);
}
