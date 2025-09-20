package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.FormationDao;
import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dao.ProjetDao;
import com.natha.dev.Dto.*;
import com.natha.dev.IService.DashboardIService;
import com.natha.dev.IService.FormationIService;
import com.natha.dev.Model.DashboardFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardImpl implements DashboardIService {

    private final BeneficiaireDao beneficiaireDao;
    private final ProjetBeneficiaireDao projetBeneficiaireDao;
    private final PayrollDao payrollDao;

    @PersistenceContext
    private EntityManager entityManager;
    private final ProjetDao projetDao;
    private final FormationIService formationService;
    private final FormationDao formationDao;

    @Override
    public List<ModuleStatsDto> getModuleStats() {
        try {
            return formationDao.getModuleStats();
        } catch (Exception e) {
            log.error("Erè pandan w ap jwenn estatistik modil yo: {}", e.getMessage());
            throw new RuntimeException("Erè pandan w ap jwenn estatistik modil yo", e);
        }
    }

    @Override
    public List<ChapitreStatsDto> getChapitreStats(
            String username, Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId) {
        try {
            // Utilisation de la nouvelle méthode avec Criteria API
            return formationDao.getChapitreStatsWithFilters(
                    composanteId, zoneId, departementId,
                    arrondissementId, communeId, sectionId,
                    quartierId, projetId
            );
        } catch (Exception e) {
            log.error("Erè pandan w ap jwenn estatistik chapit yo: {}", e.getMessage());
            throw new RuntimeException("Erè pandan w ap jwenn estatistik chapit yo", e);
        }
    }

    @Override
    public List<Object[]> executeFormationQuery(String query) {
        try {
            Query nativeQuery = entityManager.createNativeQuery(query);
            @SuppressWarnings("unchecked")
            List<Object[]> results = nativeQuery.getResultList();
            return results;
        } catch (Exception e) {
            log.error("Erè pandan ekzekisyon rekèt SQL: {}", e.getMessage());
            throw new RuntimeException("Erè pandan ekzekisyon rekèt SQL", e);
        }
    }

    @Override
    public long countModulesByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        // Premye, eseye yon konte senp pou wè si nou ka jwenn nenpòt modil
        long simpleCount = formationDao.countAllModules();

        // Si pa gen okenn modil, retounen 0
        if (simpleCount == 0) {
            return 0;
        }

        // Si gen modil, eseye ak filtre yo
        try {
            return formationDao.countModulesByFilters(
                    composanteId, zoneId, departementId,
                    arrondissementId, communeId, sectionId,
                    quartierId, projetId
            );
        } catch (Exception e) {
            // Si gen yon erè, retounen konte senp la
            log.error("Erè pandan konte modil ak filtre: {}", e.getMessage());
            return simpleCount;
        }
    }

    @Override
    public long countChapitresByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        return formationDao.countChapitresByFilters(
                composanteId, zoneId, departementId,
                arrondissementId, communeId, sectionId,
                quartierId, projetId
        );
    }

    @Override
    public List<ChapitreStatsDto> getChapitresStats(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        return formationDao.countBeneficiairesByChapitreAndSexe(
                composanteId, zoneId, departementId,
                arrondissementId, communeId, sectionId,
                quartierId, projetId
        );
    }

    @Override
    public DashboardFormationDetailsDto getFormationDetails(
            String username, Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        // Kreye yon nouvo objè DashboardFormationDetailsDto
        DashboardFormationDetailsDto detailsDto = new DashboardFormationDetailsDto();

        try {
            // Jwenn kantite fòmasyon yo ak filtre yo
            long totalFormations = countFormationsByFilters(
                    composanteId, zoneId, departementId, arrondissementId,
                    communeId, sectionId, quartierId, projetId
            );

            // Jwenn lòt estatik ou bezwen yo
            // Ekzanp: long totalParticipants = beneficiaireDao.countByFormationFilters(...);

            // Mete valè yo nan DTO a
            detailsDto.setTotalFormations(totalFormations);
            // detailsDto.setTotalParticipants(totalParticipants);
            // ... mete lòt valè ki nesesè yo

            // Log enfòmasyon an
            log.info("Detay fòmasyon yo te jwenn avèk siksè pou itilizatè: {}", username);

        } catch (Exception e) {
            log.error("Erè pandan w ap jwenn detay fòmasyon yo pou itilizatè: " + username, e);
            throw new RuntimeException("Erè pandan w ap jwenn detay fòmasyon yo", e);
        }

        return detailsDto;
    }

    @Override
    public long countProjetsAvecFormationByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        return projetDao.countProjetsAvecFormationByFilters(
                composanteId, projetId, quartierId, sectionId,
                communeId, arrondissementId, departementId, zoneId
        );
    }

    @Override
    public long countFormationsByFilters(
            Long composanteId, Long zoneId, Long departementId,
            Long arrondissementId, Long communeId, Long sectionId,
            Long quartierId, String projetId
    ) {
        return formationDao.countFormationsByFilters(
                composanteId, zoneId, departementId,
                arrondissementId, communeId, sectionId,
                quartierId, projetId
        );
    }

    @Override
    public KpiResponse getKpiData(DashboardFilter filter, String username) {
        Long composanteId = filter.getComposanteId();
        Long zoneId = filter.getZoneId();
        Long departementId = filter.getDepartementId();
        Long communeId = filter.getCommuneId();
        Long sectionId = filter.getSectionId();
        Long quartierId = filter.getQuartierId();
        Long arrondissementId = filter.getArrondissementId();
        String projetId = filter.getProjetId();

        LocalDate dateDebut = LocalDate.of(2019, 1, 1); // Required only for count methods
        LocalDate dateFin = LocalDate.now(); // Required only for count methods

        KpiResponse response = new KpiResponse();

        // --- Dashboard: Beneficiary Counts ---
        response.setTotalBeneficiaires(projetBeneficiaireDao.countBeneficiairesByFilters(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalFemmes(projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F"));
        response.setTotalHommes(projetBeneficiaireDao.countBySexe(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M"));
        response.setTotalQualifier(projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "Q"));
        response.setTotalNonQualifier(projetBeneficiaireDao.countByQualification(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "NQ"));

        // Konte pwojè yo dapre filtre yo
        response.setTotalProjets(projetDao.countProjetsByFilters(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId));

        // --- Dashboard: Detailed Beneficiary Counts for Chart ---
        response.setTotalFilleQualifier(projetBeneficiaireDao.countBySexeAndQualification(
                "F", "Q", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalFilleNonQualifier(projetBeneficiaireDao.countBySexeAndQualification(
                "F", "NQ", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalGarconQualifier(projetBeneficiaireDao.countBySexeAndQualification(
                "M", "Q", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalGarconNonQualifier(projetBeneficiaireDao.countBySexeAndQualification(
                "M", "NQ", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));

        // --- Payroll: Total Amounts by Payment Method (NO DATES) ---
        response.setTotalMonCash(payrollDao.sumByMethodePaiement(
                "MonCash", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalLajanCash(payrollDao.sumByMethodePaiement(
                "LajanCash", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
        response.setTotalMontantPaye(response.getTotalMonCash() + response.getTotalLajanCash());

        // --- Payroll: Detailed Counts by Gender and Payment Method (WITH DATES) ---
        response.setTotalFilleMoncash(payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "MonCash", dateDebut, dateFin));
        response.setTotalFilleCash(payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "LajanCash", dateDebut, dateFin));
        response.setTotalGarconMoncash(payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "MonCash", dateDebut, dateFin));
        response.setTotalGarconCash(payrollDao.countBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "LajanCash", dateDebut, dateFin));

        // --- Payroll: Total Amounts by Gender and Payment Method ---
        response.setTotalFilleMonCashMontant(payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "MonCash"));
        response.setTotalFilleLajanCashMontant(payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "LajanCash"));
        response.setTotalGarconMonCashMontant(payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "MonCash"));
        response.setTotalGarconLajanCashMontant(payrollDao.sumBySexeAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "LajanCash"));

        // --- Payroll: Detailed Amounts (Montant) by Combination (NO DATES) ---
        // Fille
        response.setTotalFilleQualifierMonCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "Q", "MonCash"));
        response.setTotalFilleQualifierLajanCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "Q", "LajanCash"));
        response.setTotalFilleNonQualifierMonCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "NQ", "MonCash"));
        response.setTotalFilleNonQualifierLajanCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "F", "NQ", "LajanCash"));

        // Garçon
        response.setTotalGarconQualifierMonCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "Q", "MonCash"));
        response.setTotalGarconQualifierLajanCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "Q", "LajanCash"));
        response.setTotalGarconNonQualifierMonCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "NQ", "MonCash"));
        response.setTotalGarconNonQualifierLajanCashMontant(payrollDao.sumBySexeAndQualificationAndMethodePaiement(
                composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId, "M", "NQ", "LajanCash"));

        // --- Formation Statistics with Filters ---
        try {
            // Jwenn estatistik fòmasyon yo ak filtre yo
            response.setTotalProjetsAvecFormation(formationDao.countProjectsWithFormationsByFilters(
                    composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
            response.setTotalFormations(formationDao.countFormationsByFilters(
                    composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
            response.setTotalModules(formationDao.countModulesByFilters(
                    composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
            response.setTotalChapitres(formationDao.countChapitresByFilters(
                    composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));

            // Jwenn kantite benefisyè ki gen fòmasyon yo
            response.setTotalBeneficiairesFormation(formationDao.countBeneficiariesWithFormationsByFilters(
                    composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));

            // Fanm ak gason ki gen fòmasyon yo
            response.setTotalFemmesFormation(formationDao.countBeneficiariesBySexeAndFormation(
                    "F", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));
            response.setTotalHommesFormation(formationDao.countBeneficiariesBySexeAndFormation(
                    "M", composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId));

            // Fanm kalifye ak pa kalifye
            long femmesQualifiees = formationDao.countBeneficiariesBySexeQualificationAndFormation(
                    "F", true, composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);
            long femmesNonQualifiees = formationDao.countBeneficiariesBySexeQualificationAndFormation(
                    "F", false, composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);

            // Gason kalifye ak pa kalifye
            long hommesQualifies = formationDao.countBeneficiariesBySexeQualificationAndFormation(
                    "M", true, composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);
            long hommesNonQualifies = formationDao.countBeneficiariesBySexeQualificationAndFormation(
                    "M", false, composanteId, zoneId, departementId, arrondissementId, communeId, sectionId, quartierId, projetId);

            // Debug log
            log.info("Fanm kalifye: {}, Fanm pa kalifye: {}, Gason kalifye: {}, Gason pa kalifye: {}",
                    femmesQualifiees, femmesNonQualifiees, hommesQualifies, hommesNonQualifies);

            response.setTotalFemmesQualifieesFormation(femmesQualifiees);
            response.setTotalFemmesNonQualifieesFormation(femmesNonQualifiees);
            response.setTotalHommesQualifiesFormation(hommesQualifies);
            response.setTotalHommesNonQualifiesFormation(hommesNonQualifies);

        } catch (Exception e) {
            log.error("Erè pandan w ap jwenn estatistik fòmasyon yo: {}", e.getMessage());
            // Si gen yon erè, nou pa vle kraze tout kòd la, nou jis kontinye
        }

        return response;
    }

    @Override
    public long countProjets(DashboardFilter filter) {
        return projetDao.countProjetsByFilters(
                filter.getComposanteId(),
                filter.getZoneId(),
                filter.getDepartementId(),
                filter.getArrondissementId(),
                filter.getCommuneId(),
                filter.getSectionId(),
                filter.getQuartierId()
        );
    }

    @Override
    public KpiResponse getFormationStatistics() {
        // Kreye yon nouvo KpiResponse
        KpiResponse response = new KpiResponse();

        try {
            // Jwenn tout estatistik yo dirèkteman nan baz done a
            response.setTotalProjetsAvecFormation(formationDao.countProjectsWithFormations());
            response.setTotalFormations(formationDao.countDistinctFormations());
            response.setTotalModules(formationDao.countAllModules());
            response.setTotalChapitres(formationDao.countAllChapitres());
            response.setTotalBeneficiairesFormation(formationDao.countBeneficiariesWithFormations());
            response.setTotalFemmesFormation(formationDao.countFemaleBeneficiariesWithFormations());
            response.setTotalHommesFormation(formationDao.countMaleBeneficiariesWithFormations());
            response.setTotalFemmesQualifieesFormation(formationDao.countQualifiedFemaleBeneficiaries());
            response.setTotalFemmesNonQualifieesFormation(formationDao.countNonQualifiedFemaleBeneficiaries());
            response.setTotalHommesQualifiesFormation(formationDao.countQualifiedMaleBeneficiaries());
            response.setTotalHommesNonQualifiesFormation(formationDao.countNonQualifiedMaleBeneficiaries());
        } catch (Exception e) {
            // Si gen yon erè, nou pa vle kraze tout kòd la, nou jis retounen repons la vid
            // Men, nou ka ajoute yon log pou wè erè a nan konsòl la
            System.err.println("Erè nan getFormationStatistics: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
}
