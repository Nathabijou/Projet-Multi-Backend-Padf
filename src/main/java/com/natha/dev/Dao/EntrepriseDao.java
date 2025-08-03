package com.natha.dev.Dao;

import com.natha.dev.Model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrepriseDao extends JpaRepository<Entreprise, Long> {
    boolean existsByNom(String nom);
    Optional<Entreprise> findById(Long id);
}
