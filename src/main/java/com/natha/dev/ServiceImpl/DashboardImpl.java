package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BeneficiaireDao;
import com.natha.dev.Dao.PayrollDao;
import com.natha.dev.Dao.ProjetBeneficiaireDao;
import com.natha.dev.Dto.KpiResponse;
import com.natha.dev.IService.DashboardIService;
import com.natha.dev.Model.DashboardFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardImpl implements DashboardIService {

    @Autowired
    private BeneficiaireDao beneficiaireDao;

    @Autowired
    private ProjetBeneficiaireDao projetBeneficiaireDao;

    @Autowired
    private PayrollDao payrollDao;

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

        return response;
    }
}
