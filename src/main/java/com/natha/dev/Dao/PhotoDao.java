package com.natha.dev.Dao;

import com.natha.dev.Model.Photo;
import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoDao extends JpaRepository<Photo, Long> {

    /**
     * Jwenn tout foto yon pwojè espesifik
     * @param projet Pwojè a pou chache foto yo
     * @return Yon lis tout foto ki asosye ak pwojè a
     */
    List<Photo> findByProjet(Projet projet);

    /**
     * Jwenn tout foto yon pwojè espesifik selon kalite foto a
     * @param projet Pwojè a pou chache foto yo
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Yon lis foto ki matche ak kritè yo
     */
    List<Photo> findByProjetAndType(Projet projet, Photo.PhotoType type);

    /**
     * Jwenn tout foto yon pwojè espesifik dapre ID pwojè a
     * @param projetId ID pwojè a
     * @return Yon lis tout foto ki asosye ak pwojè a
     */
    @Query("SELECT p FROM Photo p WHERE p.projet.idProjet = :projetId")
    List<Photo> findByProjetId(@Param("projetId") String projetId);

    /**
     * Jwenn tout foto yon pwojè espesifik ak yon kalite espesifik dapre ID pwojè a
     * @param projetId ID pwojè a
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Yon lis foto ki matche ak kritè yo
     */
    @Query("SELECT p FROM Photo p WHERE p.projet.idProjet = :projetId AND p.type = :type")
    List<Photo> findByProjetIdAndType(@Param("projetId") String projetId, @Param("type") Photo.PhotoType type);

    /**
     * Jwenn yon foto pa ID li ak ID pwojè a
     * @param id ID foto a
     * @param projet Pwojè ki posede foto a
     * @return Foto a si li egziste, otreman Optional vid
     */
    Optional<Photo> findByIdAndProjet(Long id, Projet projet);

    /**
     * Efase tout foto yon pwojè espesifik
     * @param projet Pwojè a pou efase foto yo
     */
    @Modifying
    @Query("DELETE FROM Photo p WHERE p.projet = :projet")
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
    Optional<Photo> findById(Long id);
}
