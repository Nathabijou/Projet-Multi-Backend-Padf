package com.natha.dev.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class KpiResponse {

    // --- Dashboard: Beneficiary Counts ---
    private long totalBeneficiaires;
    private long totalFemmes;
    private long totalHommes;
    private long totalQualifier;
    private long totalNonQualifier;

    // --- Dashboard: Detailed Beneficiary Counts for Chart ---
    private long totalFilleQualifier;
    private long totalFilleNonQualifier;
    private long totalGarconQualifier;
    private long totalGarconNonQualifier;

    // --- Payroll: Total Amounts by Payment Method ---
    private double totalMonCash;
    private double totalLajanCash;
    private double totalMontantPaye;

    // --- Payroll: Detailed Counts by Gender and Payment Method ---
    private long totalFilleMoncash;
    private long totalFilleCash;
    private long totalGarconMoncash;
    private long totalGarconCash;

    // --- Payroll: Total Amounts by Gender and Payment Method ---
    private double totalFilleMonCashMontant;
    private double totalFilleLajanCashMontant;
    private double totalGarconMonCashMontant;
    private double totalGarconLajanCashMontant;

    // --- Dashboard: Project Counts ---
    private long totalProjets;

    // --- Payroll: Detailed Amounts (Montant) by Combination ---
    // Fille
    private double totalFilleQualifierMonCashMontant;
    private double totalFilleQualifierLajanCashMontant;
    private double totalFilleNonQualifierMonCashMontant;
    private double totalFilleNonQualifierLajanCashMontant;

    // Garçon
    private double totalGarconQualifierMonCashMontant;
    private double totalGarconQualifierLajanCashMontant;
    private double totalGarconNonQualifierMonCashMontant;
    private double totalGarconNonQualifierLajanCashMontant;

    // --- Formation Statistics ---
    private long totalProjetsAvecFormation;
    private long totalFormations;
    private long totalModules;
    private long totalChapitres;
    private long totalBeneficiairesFormation;
    private long totalFemmesFormation;
    private long totalHommesFormation;
    private long totalFemmesQualifieesFormation;
    private long totalFemmesNonQualifieesFormation;
    private long totalHommesQualifiesFormation;
    private long totalHommesNonQualifiesFormation;

    // --- Module Statistics ---
    private Map<String, Object> beneficiairesByModule = new HashMap<>();
    private List<ModuleStatsDto> moduleStats = new ArrayList<>();

}
