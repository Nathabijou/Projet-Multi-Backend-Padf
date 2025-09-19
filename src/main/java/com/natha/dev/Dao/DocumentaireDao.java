package com.natha.dev.Dao;

import com.natha.dev.Model.Documentaire;
import com.natha.dev.Model.Photo;
import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentaireDao extends JpaRepository<Documentaire, Long> {
    /**
     * Jwenn tout foto yon pwojè espesifik
     * @param projet Pwojè a pou chache foto yo
     * @return Yon lis tout foto ki asosye ak pwojè a
     */
    List<Documentaire> findByProjet(Projet projet);

    /**
     * Jwenn tout dokiman yon pwojè espesifik selon kalite dokiman an
     * @param projet Pwojè a pou chache dokiman yo
     * @param type Kalite dokiman an (FACTURE, DEVIS, CONTRAT, RAPPORT, AUTRE)
     * @return Yon lis dokiman ki matche ak kritè yo
     */
    List<Documentaire> findByProjetAndType(Projet projet, Documentaire.DocumentaireType type);

    /**
     * Jwenn tout dokiman yon pwojè espesifik dapre ID pwojè a
     * @param projetId ID pwojè a
     * @return Yon lis tout dokiman ki asosye ak pwojè a
     */
    @Query("SELECT d FROM Documentaire d WHERE d.projet.idProjet = :projetId")
    List<Documentaire> findByProjetId(@Param("projetId") String projetId);

    /**
     * Jwenn tout dokiman yon pwojè espesifik dapre ID pwojè a ak kalite dokiman an
     * @param projetId ID pwojè a
     * @param type Kalite dokiman an (FACTURE, DEVIS, CONTRAT, RAPPORT, AUTRE)
     * @return Yon lis dokiman ki matche ak kritè yo
     */
    @Query("SELECT d FROM Documentaire d WHERE d.projet.idProjet = :projetId AND d.type = :type")
    List<Documentaire> findByProjetIdAndType(@Param("projetId") String projetId, @Param("type") Documentaire.DocumentaireType type);

    /**
     * Jwenn yon foto pa ID li ak ID pwojè a
     * @param id ID foto a
     * @param projet Pwojè ki posede foto a
     * @return Foto a si li egziste, otreman Optional vid
     */
    Optional<Documentaire> findByIdAndProjet(Long id, Projet projet);

    /**
     * Efase tout foto yon pwojè espesifik
     * @param projet Pwojè a pou efase foto yo
     */
    @Modifying
    @Query("DELETE FROM Documentaire d WHERE d.projet = :projet")
    void deleteByProjet(@Param("projet") Projet projet);

    /**
     * Konte konbyen foto yon pwojè genyen
     * @param projet Pwojè a pou konte foto yo
     * @return Kantite foto pwojè a genyen
     */
    long countByProjet(Projet projet);

    /**
     * Jwenn yon foto pa ID li
     * @param id ID foto a
     * @return Foto a si li egziste, otreman Optional vid
     */
    @Override
    Optional<Documentaire> findById(Long id);
}
