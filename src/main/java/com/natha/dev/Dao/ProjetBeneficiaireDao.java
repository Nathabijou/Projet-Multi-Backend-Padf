package com.natha.dev.Dao;

import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireDao extends JpaRepository<ProjetBeneficiaire, String> {
    List<ProjetBeneficiaire> findByProjetIdProjet(String projetId);

    Optional<ProjetBeneficiaire> findByProjetIdProjetAndBeneficiaireIdBeneficiaire(String projetId, String beneficiaireId);

    @Query("""
SELECT COUNT(pb) FROM ProjetBeneficiaire pb
JOIN pb.projet.quartier.sectionCommunale.commune c
LEFT JOIN c.arrondissement a
LEFT JOIN a.departement d
LEFT JOIN d.zones z
WHERE pb.beneficiaire.sexe = :sexe
  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR z.id = :zoneId)
  AND (:departementId IS NULL OR d.id = :departementId)
  AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
  AND (:communeId IS NULL OR c.id = :communeId)
  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBySexe(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe
    );

    @Query("""
SELECT COUNT(pb) FROM ProjetBeneficiaire pb
JOIN pb.projet.quartier.sectionCommunale.commune c
LEFT JOIN c.arrondissement a
LEFT JOIN a.departement d
LEFT JOIN d.zones z
WHERE pb.beneficiaire.qualification = :qualification
  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR z.id = :zoneId)
  AND (:departementId IS NULL OR d.id = :departementId)
  AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
  AND (:communeId IS NULL OR c.id = :communeId)
  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countByQualification(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("qualification") String qualification
    );

    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    JOIN pb.projet.quartier.sectionCommunale.commune c
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zones z
    WHERE (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR z.id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBeneficiairesByFilters(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    @Query("""
    SELECT COUNT(pb) FROM ProjetBeneficiaire pb
    JOIN pb.beneficiaire b
    JOIN pb.projet.quartier.sectionCommunale.commune c
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zones z
    WHERE b.sexe = :sexe
      AND b.qualification = :qualification
      AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR z.id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
""")
    long countBySexeAndQualification(
            @Param("sexe") String sexe,
            @Param("qualification") String qualification,
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );
}
