package com.natha.dev.Dao;

import com.natha.dev.Dto.PayrollFlatData;
import com.natha.dev.Dto.PeriodeMethodePaiementDTO;
import com.natha.dev.Model.Payroll;
import com.natha.dev.Model.ProjetBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PayrollDao extends JpaRepository<Payroll, String> {
    List<Payroll> findByProjetBeneficiaire(ProjetBeneficiaire projetBeneficiaire);
    Optional<Payroll> findById(String id);

    @Query("""
    SELECT COALESCE(SUM(p.montantPayer * p.nbreJourTravail), 0) FROM Payroll p
    LEFT JOIN p.projetBeneficiaire.projet.composanteProjets cp
    LEFT JOIN cp.composante comp
    WHERE p.methodePaiement = :methode
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.arrondissement.departement AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId)
      AND (:arrondissementId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId)
      AND (:communeId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR p.projetBeneficiaire.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR p.projetBeneficiaire.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR p.projetBeneficiaire.projet.id = :projetId)
    """)
    double sumByMethodePaiement(
            @Param("methode") String methode,
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
    SELECT COALESCE(SUM(p.montantPayer * p.nbreJourTravail), 0) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    LEFT JOIN pb.projet.composanteProjets cp
    LEFT JOIN cp.composante comp
    WHERE b.sexe = :sexe
      AND p.methodePaiement = :methodePaiement
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = pb.projet.quartier.sectionCommunale.commune.arrondissement.departement AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId)
      AND (:arrondissementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
    """)
    double sumBySexeAndMethodePaiement(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe,
            @Param("methodePaiement") String methodePaiement
    );



    @Query("""
    SELECT COALESCE(SUM(p.montantPayer * p.nbreJourTravail), 0) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    LEFT JOIN pb.projet.composanteProjets cp
    LEFT JOIN cp.composante comp
    WHERE b.sexe = :sexe
      AND b.qualification = :qualification
      AND p.methodePaiement = :methodePaiement
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = pb.projet.quartier.sectionCommunale.commune.arrondissement.departement AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId)
      AND (:arrondissementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
    """)
    double sumBySexeAndQualificationAndMethodePaiement(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe,
            @Param("qualification") String qualification,
            @Param("methodePaiement") String methodePaiement
    );



    @Query("""
    SELECT COUNT(p) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    JOIN pb.beneficiaire b
    LEFT JOIN pb.projet.composanteProjets cp
    LEFT JOIN cp.composante comp
    WHERE b.sexe IS NOT NULL
      AND (:sexe IS NULL OR b.sexe = :sexe)
      AND (:methodePaiement IS NULL OR p.methodePaiement = :methodePaiement)
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = pb.projet.quartier.sectionCommunale.commune.arrondissement.departement AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId)
      AND (:arrondissementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
      AND p.debutPeriode >= :startDate
      AND p.debutPeriode <= :endDate
    """)
    long countBySexeAndMethodePaiement(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId,
            @Param("sexe") String sexe,
            @Param("methodePaiement") String methodePaiement,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    @Query("""
    SELECT new com.natha.dev.Dto.PayrollFlatData(
    pr.name, p.debutPeriode, p.finPeriode, 
    b.nom, b.prenom, b.sexe, b.qualification, 
    p.nbreJourTravail, p.montantPayer, 
    b.identification, b.telephonePaiement, p.methodePaiement,
    p.statut,
    b.telephoneContact
    )
    FROM Payroll p 
    JOIN p.projetBeneficiaire pb 
    JOIN pb.beneficiaire b
    JOIN pb.projet pr
    WHERE pr.idProjet = :projetId""")
    List<PayrollFlatData> findPayrollDataByProjet(@Param("projetId") String projetId);

    @Query("SELECT p FROM Payroll p WHERE p.projetBeneficiaire.projet.idProjet = :projetId")
    List<Payroll> findByProjetId(@Param("projetId") String projetId);

    @Query("""
    SELECT new com.natha.dev.Dto.PeriodeMethodePaiementDTO(p.debutPeriode, p.finPeriode, 
        (SELECT p2.methodePaiement 
         FROM Payroll p2 
         WHERE p2.projetBeneficiaire.projet.idProjet = :projetId 
           AND p2.debutPeriode = p.debutPeriode 
           AND p2.finPeriode = p.finPeriode 
         GROUP BY p2.methodePaiement 
         ORDER BY COUNT(p2.methodePaiement) DESC 
         LIMIT 1)) 
    FROM Payroll p 
    WHERE p.projetBeneficiaire.projet.idProjet = :projetId 
    GROUP BY p.debutPeriode, p.finPeriode
    """)
    List<PeriodeMethodePaiementDTO> findPeriodeAndMethodeByProjet(@Param("projetId") String projetId);

    // Metòd pou konte payroll pa lokasyon
    @Query("""
    SELECT COUNT(DISTINCT p.idPayroll) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    LEFT JOIN pb.projet.composanteProjets cp
    LEFT JOIN cp.composante comp
    WHERE (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR EXISTS (SELECT 1 FROM ZoneDepartement zd WHERE zd.departement = pb.projet.quartier.sectionCommunale.commune.arrondissement.departement AND zd.zone.id = :zoneId))
      AND (:departementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.departement.id = :departementId)
      AND (:arrondissementId IS NULL OR pb.projet.quartier.sectionCommunale.commune.arrondissement.id = :arrondissementId)
      AND (:communeId IS NULL OR pb.projet.quartier.sectionCommunale.commune.id = :communeId)
      AND (:sectionId IS NULL OR pb.projet.quartier.sectionCommunale.id = :sectionId)
      AND (:quartierId IS NULL OR pb.projet.quartier.id = :quartierId)
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
    """)
    long countPayrollByLocation(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    // Metòd pou konte beneficiaire pa lokasyon (sèlman beneficiaire ki gen pi piti yon jou de travail)
    @Query("""
    SELECT COUNT(DISTINCT pb.beneficiaire.idBeneficiaire) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    LEFT JOIN pb.projet pr
    LEFT JOIN pr.composanteProjets cp
    LEFT JOIN cp.composante comp
    LEFT JOIN pr.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune c
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE p.nbreJourTravail > 0
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR z.id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR sc.id = :sectionId)
      AND (:quartierId IS NULL OR q.id = :quartierId)
      AND (:projetId IS NULL OR pr.id = :projetId)
    """)
    long countBeneficiaireByLocation(
            @Param("composanteId") Long composanteId,
            @Param("zoneId") Long zoneId,
            @Param("departementId") Long departementId,
            @Param("arrondissementId") Long arrondissementId,
            @Param("communeId") Long communeId,
            @Param("sectionId") Long sectionId,
            @Param("quartierId") Long quartierId,
            @Param("projetId") String projetId
    );

    // Metòd pou konte peryòd payroll unik pa projet (sèlman peryòd ki gen beneficiaire ak jou de travail)
    @Query("""
    SELECT COUNT(DISTINCT CONCAT(p.debutPeriode, '-', p.finPeriode)) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    WHERE p.nbreJourTravail > 0
      AND (:projetId IS NULL OR pb.projet.id = :projetId)
    """)
    long countPayrollPeriodesByProjet(
            @Param("projetId") String projetId
    );

    // Metòd pou konte peryòd payroll unik pa lokasyon (sèlman peryòd ki gen beneficiaire ak jou de travail)
    @Query("""
    SELECT COUNT(DISTINCT CONCAT(p.debutPeriode, '-', p.finPeriode)) FROM Payroll p
    JOIN p.projetBeneficiaire pb
    LEFT JOIN pb.projet pr
    LEFT JOIN pr.composanteProjets cp
    LEFT JOIN cp.composante comp
    LEFT JOIN pr.quartier q
    LEFT JOIN q.sectionCommunale sc
    LEFT JOIN sc.commune c
    LEFT JOIN c.arrondissement a
    LEFT JOIN a.departement d
    LEFT JOIN d.zoneDepartements zd
    LEFT JOIN zd.zone z
    WHERE p.nbreJourTravail > 0
      AND (:composanteId IS NULL OR comp.id = :composanteId)
      AND (:zoneId IS NULL OR z.id = :zoneId)
      AND (:departementId IS NULL OR d.id = :departementId)
      AND (:arrondissementId IS NULL OR a.id = :arrondissementId)
      AND (:communeId IS NULL OR c.id = :communeId)
      AND (:sectionId IS NULL OR sc.id = :sectionId)
      AND (:quartierId IS NULL OR q.id = :quartierId)
      AND (:projetId IS NULL OR pr.id = :projetId)
    """)
    long countPayrollPeriodesByLocation(
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