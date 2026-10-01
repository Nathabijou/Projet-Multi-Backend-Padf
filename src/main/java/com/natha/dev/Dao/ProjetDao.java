package com.natha.dev.Dao;

import com.natha.dev.Model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProjetDao extends JpaRepository<Projet, String> {
    @Query("SELECT p FROM Projet p JOIN p.composanteProjets cp JOIN cp.composante c WHERE c.id = :composanteId")
    List<Projet> findByComposanteId(@Param("composanteId") Long composanteId);

    @Query("SELECT p FROM Projet p JOIN p.composanteProjets cp JOIN cp.composante c WHERE c.id = :composanteId AND p.quartier.id = :quartierId")
    List<Projet> findByComposanteIdAndQuartierId(@Param("composanteId") Long composanteId, @Param("quartierId") String quartierId);

    @Query("SELECT p FROM Projet p JOIN p.quartier q JOIN q.sectionCommunale sc JOIN sc.commune c JOIN c.arrondissement a JOIN a.departement d JOIN d.zoneDepartements zd JOIN zd.zone z WHERE z.id = :zoneId")
    List<Projet> findByZoneId(@Param("zoneId") Long zoneId);

    @Query("SELECT p FROM Projet p WHERE p.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId")
    List<Projet> findByArrondissementId(@Param("arrondissementId") Long arrondissementId);

    @Query("SELECT p FROM Projet p WHERE p.quartier.sectionCommunale.commune.id = :communeId")
    List<Projet> findByCommuneId(@Param("communeId") Long communeId);

    @Query("SELECT p FROM Projet p WHERE p.quartier.sectionCommunale.id = :sectionId")
    List<Projet> findBySectionCommunaleId(@Param("sectionId") Long sectionId);

    @Query("SELECT p FROM Projet p JOIN p.quartier q JOIN q.sectionCommunale sc JOIN sc.commune c JOIN c.arrondissement a JOIN a.departement d WHERE d.id = :departementId")
    List<Projet> findByDepartementId(@Param("departementId") Long departementId);

    List<Projet> findAllByActiveTrue();
    Optional<Projet> findById(String idProjet);

    @Query("""
    SELECT COUNT(DISTINCT p) 
    FROM Projet p 
    JOIN p.projetBeneficiaires pb
    JOIN pb.projetBeneficiaireFormations pbf
    LEFT JOIN p.composanteProjets cp
    LEFT JOIN cp.composante comp
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune c
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE 1=1
    AND (:composanteId IS NULL OR comp.id = :composanteId)
    AND (:projetId IS NULL OR p.idProjet = :projetId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:communeId IS NULL OR c.id = :communeId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    """)
    long countProjetsAvecFormationByFilters(
            @Param("composanteId") Long composanteId,
            @Param("projetId") String projetId,
            @Param("quartierId") Long quartierId,
            @Param("sectionId") Long sectionId,
            @Param("communeId") Long communeId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("departementId") Long departementId,
            @Param("zoneId") Long zoneId
    );

    @Query("""
        SELECT COUNT(DISTINCT p) FROM Projet p 
        LEFT JOIN p.composanteProjets cp
        LEFT JOIN cp.composante comp
        LEFT JOIN p.quartier q 
        LEFT JOIN q.sectionCommunale sc 
        LEFT JOIN sc.commune c 
        LEFT JOIN c.arrondissement a 
        LEFT JOIN a.departement d 
        LEFT JOIN d.zoneDepartements zd 
        LEFT JOIN zd.zone z 
        WHERE (:composanteId IS NULL OR comp.id = :composanteId)
        AND (:zoneId IS NULL OR z.id = :zoneId)
        AND (:departementId IS NULL OR d.id = :departementId)
        AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
        AND (:communeId IS NULL OR c.id = :communeId)
        AND (:sectionId IS NULL OR sc.id = :sectionId)
        AND (:quartierId IS NULL OR q.id = :quartierId)
    """)
    Long countProjetsByFilters(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId
    );
}
