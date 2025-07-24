package com.natha.dev.Dao;

import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireDao extends JpaRepository<ProjetBeneficiaire, String> {
    List<ProjetBeneficiaire> findByProjetIdProjet(String projetId);

    @Query("SELECT pb FROM ProjetBeneficiaire pb WHERE pb.projet.idProjet = :projetId AND pb.beneficiaire.idBeneficiaire = :beneficiaireId")
    Optional<ProjetBeneficiaire> findByProjetAndBeneficiaire(@Param("projetId") String projetId, @Param("beneficiaireId") String beneficiaireId);

    @Query("""
SELECT COUNT(pb) FROM ProjetBeneficiaire pb
JOIN pb.projet.quartier.sectionCommunale.commune c
LEFT JOIN c.arrondissement a
LEFT JOIN a.departement d
WHERE pb.beneficiaire.sexe = :sexe
  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = d AND zd.zone.id = :zoneId))
  AND (:departementId IS NULL OR d.id = :departementId)
  AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
  AND (:communeId IS NULL OR c.id = :communeId)
  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR pb.projet.idProjet = :projetId)
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
WHERE pb.beneficiaire.qualification = :qualification
  AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
  AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = d AND zd.zone.id = :zoneId))
  AND (:departementId IS NULL OR d.id = :departementId)
  AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
  AND (:communeId IS NULL OR c.id = :communeId)
  AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
  AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
  AND (:projetId IS NULL OR pb.projet.idProjet = :projetId)
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
    WHERE (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = d AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.idProjet = :projetId)
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
    WHERE b.sexe = :sexe
      AND b.qualification = :qualification
      AND (:composanteId IS NULL OR pb.projet.composante.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = d AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.idProjet = :projetId)
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
