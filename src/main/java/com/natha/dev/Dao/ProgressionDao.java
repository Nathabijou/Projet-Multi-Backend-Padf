package com.natha.dev.Dao;

import com.natha.dev.Model.Progression;
import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressionDao extends JpaRepository<Progression, String> {
    List<Progression> findByProjet(Projet projet);
    List<Progression> findByProjetIdProjet(String projetId);
    Optional<Progression> findById(String id);
}
