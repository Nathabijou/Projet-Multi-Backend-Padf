package com.natha.dev.Dao;

import com.natha.dev.Dto.ChapitreStatsDto;
import com.natha.dev.Dto.DashboardFormationDetailsDto;
import com.natha.dev.Dto.ModuleStatsDto;
import com.natha.dev.Model.Formation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormationDao extends JpaRepository<Formation, String>, FormationDaoCustom {

    @Query("""
    SELECT new com.natha.dev.Dto.ModuleStatsDto(
        m.idModule,
        m.titre,
        (SELECT COUNT(DISTINCT mb1.beneficiaire.id) 
         FROM ModuleBeneficiaire mb1 
         JOIN Beneficiaire b1 ON mb1.beneficiaire.id = b1.id 
         WHERE mb1.module.id = m.idModule AND b1.sexe = 'F'),
        (SELECT COUNT(DISTINCT mb2.beneficiaire.id) 
         FROM ModuleBeneficiaire mb2 
         JOIN Beneficiaire b2 ON mb2.beneficiaire.id = b2.id 
         WHERE mb2.module.id = m.idModule AND b2.sexe = 'F' AND mb2.isCompleted = true),
        (SELECT COUNT(DISTINCT mb3.beneficiaire.id) 
         FROM ModuleBeneficiaire mb3 
         JOIN Beneficiaire b3 ON mb3.beneficiaire.id = b3.id 
         WHERE mb3.module.id = m.idModule AND b3.sexe = 'M'),
        (SELECT COUNT(DISTINCT mb4.beneficiaire.id) 
         FROM ModuleBeneficiaire mb4 
         JOIN Beneficiaire b4 ON mb4.beneficiaire.id = b4.id 
         WHERE mb4.module.id = m.idModule AND b4.sexe = 'M' AND mb4.isCompleted = true)
    )
    FROM Module m
    """)
    List<ModuleStatsDto> getModuleStats();


    @Query("SELECT COUNT(DISTINCT pbf.formation) FROM ProjetBeneficiaireFormation pbf")
    long countFormationsWithBeneficiaries();

    @Query("SELECT COUNT(DISTINCT pbf.formation) FROM ProjetBeneficiaireFormation pbf WHERE pbf.formation IS NOT NULL")
    long countDistinctFormations();


    @Query("""
    SELECT DISTINCT p.id, p.name 
    FROM ProjetBeneficiaireFormation pbf 
    JOIN pbf.formation f
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    List<Object[]> findProjectsWithFormations(
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
    SELECT DISTINCT f.idFormation, f.titre, COUNT(DISTINCT pbf.projetBeneficiaire.beneficiaire),
           COUNT(DISTINCT CASE WHEN b.sexe = 'F' THEN pbf.projetBeneficiaire.beneficiaire END),
           COUNT(DISTINCT CASE WHEN b.sexe = 'M' THEN pbf.projetBeneficiaire.beneficiaire END)
    FROM ProjetBeneficiaireFormation pbf
    JOIN pbf.formation f
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    GROUP BY f.idFormation, f.titre
    """)
    List<Object[]> findFormationDetails(
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
    SELECT m.idModule, m.titre, c.idChapitre, c.titre,
           COUNT(DISTINCT pbf.projetBeneficiaire.beneficiaire)
    FROM Module m
    JOIN m.chapitre c
    JOIN c.formation f
    JOIN ProjetBeneficiaireFormation pbf ON pbf.formation = f
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    AND f.idFormation = :formationId
    GROUP BY m.idModule, m.titre, m.ordre, c.idChapitre, c.titre, c.ordre
    ORDER BY c.ordre, m.ordre
    """)
    List<Object[]> findModuleAndChapitreDetails(
            @Param("formationId") String formationId,
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    @Query("SELECT COUNT(DISTINCT pbf.projetBeneficiaire.projet) FROM ProjetBeneficiaireFormation pbf")
    long countProjectsWithFormations();

    @Query("""
    SELECT COUNT(DISTINCT c) 
    FROM Chapitre c
    JOIN c.formation f
    JOIN f.projetBeneficiaireFormations pbf
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    long countChapitresByFilters(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    @Query("SELECT COUNT(DISTINCT c) FROM Chapitre c")
    long countAllChapitres();

    @Query("SELECT COUNT(m) FROM Module m WHERE m.idModule IS NOT NULL")
    long countAllModules();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
    )""")
    long countBeneficiariesWithFormations();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'F'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
    )""")
    long countFemaleBeneficiariesWithFormations();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'M'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
    )""")
    long countMaleBeneficiariesWithFormations();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'F'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
        AND pbf.noteFinale >= 70
    )""")
    long countQualifiedFemaleBeneficiaries();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'F'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
        AND (pbf.noteFinale < 70 OR pbf.noteFinale IS NULL)
    )""")
    long countNonQualifiedFemaleBeneficiaries();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'M'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
        AND pbf.noteFinale >= 70
    )""")
    long countQualifiedMaleBeneficiaries();

    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    WHERE pb.beneficiaire.sexe = 'M'
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
        AND (pbf.noteFinale < 70 OR pbf.noteFinale IS NULL)
    )""")
    long countNonQualifiedMaleBeneficiaries();

    @Query("SELECT COUNT(pbf) FROM ProjetBeneficiaireFormation pbf")
    long countAllFormationRecords();

    @Query("""
    SELECT NEW com.natha.dev.Dto.ChapitreStatsDto(
        c.idChapitre,
        c.titre,
        SUM(CASE WHEN b.sexe = 'F' THEN 1 ELSE 0 END),
        SUM(CASE WHEN b.sexe = 'M' THEN 1 ELSE 0 END)
    )
    FROM Chapitre c
    JOIN c.formation f
    JOIN f.projetBeneficiaireFormations pbf
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    GROUP BY c.idChapitre, c.titre
    """)
    List<ChapitreStatsDto> countBeneficiairesByChapitreAndSexe(
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
    SELECT COUNT(DISTINCT p) 
    FROM ProjetBeneficiaireFormation pbf
    JOIN pbf.projetBeneficiaire pb
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    long countProjectsWithFormationsByFilters(
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
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    JOIN pb.projet p
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
    )
    AND (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    long countBeneficiariesWithFormationsByFilters(
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
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    JOIN pb.projet p
    JOIN pb.beneficiaire b
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE b.sexe = :sexe
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
    )
    AND (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    long countBeneficiariesBySexeAndFormation(
            @Param("sexe") String sexe,
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
    SELECT COUNT(DISTINCT pb.beneficiaire.id) 
    FROM ProjetBeneficiaire pb
    JOIN pb.projet p
    JOIN pb.beneficiaire b
    LEFT JOIN p.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune comm
    LEFT JOIN comm.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE b.sexe = :sexe
    AND EXISTS (
        SELECT 1 FROM ProjetBeneficiaireFormation pbf 
        WHERE pbf.projetBeneficiaire.id = pb.id
        AND (
            (:isQualified = TRUE AND pbf.noteFinale IS NOT NULL AND pbf.noteFinale >= 70.0) 
            OR 
            (:isQualified = FALSE AND (pbf.noteFinale IS NULL OR pbf.noteFinale < 70.0))
        )
    )
    AND (:composanteId IS NULL OR p.composante.id = :composanteId)
    AND (:zoneId IS NULL OR z.id = :zoneId)
    AND (:departementId IS NULL OR d.id = :departementId)
    AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
    AND (:communeId IS NULL OR comm.id = :communeId)
    AND (:sectionId IS NULL OR sc.id = :sectionId)
    AND (:quartierId IS NULL OR q.id = :quartierId)
    AND (:projetId IS NULL OR p.id = :projetId)
    """)
    @Transactional(readOnly = true)
    long countBeneficiariesBySexeQualificationAndFormation(
            @Param("sexe") String sexe,
            @Param("isQualified") boolean isQualified,
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
SELECT COUNT(DISTINCT pbf.formation) 
FROM ProjetBeneficiaireFormation pbf
JOIN pbf.projetBeneficiaire pb
JOIN pb.projet p
LEFT JOIN p.quartier q
LEFT JOIN q.sectionCommunale sc
LEFT JOIN sc.commune comm
LEFT JOIN comm.arrondissement a
LEFT JOIN a.departement d
LEFT JOIN d.zoneDepartements zd
LEFT JOIN zd.zone z
WHERE (:composanteId IS NULL OR p.composante.id = :composanteId)
AND (:zoneId IS NULL OR z.id = :zoneId)
AND (:departementId IS NULL OR d.id = :departementId)
AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
AND (:communeId IS NULL OR comm.id = :communeId)
AND (:sectionId IS NULL OR sc.id = :sectionId)
AND (:quartierId IS NULL OR q.id = :quartierId)
AND (:projetId IS NULL OR p.id = :projetId)
""")
    long countFormationsByFilters(
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
    SELECT COUNT(DISTINCT m) 
    FROM Module m
    WHERE (:projetId IS NULL OR EXISTS (
        SELECT 1 FROM m.chapitre c 
        JOIN c.formation f 
        JOIN f.projetBeneficiaireFormations pbf 
        JOIN pbf.projetBeneficiaire pb 
        JOIN pb.projet p 
        WHERE p.id = :projetId
        AND (:composanteId IS NULL OR p.composante.id = :composanteId)
        AND (:zoneId IS NULL OR EXISTS (
            SELECT 1 FROM p.quartier q 
            JOIN q.sectionCommunale sc 
            JOIN sc.commune comm 
            JOIN comm.arrondissement a 
            JOIN a.departement d 
            JOIN d.zoneDepartements zd 
            JOIN zd.zone z 
            WHERE z.id = :zoneId
            AND (:departementId IS NULL OR d.id = :departementId)
            AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
            AND (:communeId IS NULL OR comm.id = :communeId)
            AND (:sectionId IS NULL OR sc.id = :sectionId)
            AND (:quartierId IS NULL OR q.id = :quartierId)
        ))
    ))
    OR (NOT EXISTS (
        SELECT 1 FROM m.chapitre c 
        JOIN c.formation f 
        JOIN f.projetBeneficiaireFormations pbf 
        JOIN pbf.projetBeneficiaire pb 
        JOIN pb.projet p
    ) AND :projetId IS NULL 
    AND :composanteId IS NULL 
    AND :zoneId IS NULL 
    AND :departementId IS NULL 
    AND :arrondissementId IS NULL 
    AND :communeId IS NULL 
    AND :sectionId IS NULL 
    AND :quartierId IS NULL)
    """)
    long countModulesByFilters(
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
