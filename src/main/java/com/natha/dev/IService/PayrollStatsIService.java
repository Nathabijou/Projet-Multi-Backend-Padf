package com.natha.dev.IService;

import com.natha.dev.Dto.PayrollStatsDto;

import java.util.List;

public interface PayrollStatsIService {

    // Kalkile Nombre de payroll par Composante
    PayrollStatsDto getPayrollStatsByComposante(Long composanteId);

    // Kalkile Nombre de payroll par Zone
    List<PayrollStatsDto> getPayrollStatsByZone(Long zoneId);

    // Kalkile Nombre de payroll par Departement (ak filtre composante ak zone opsyonèl)
    List<PayrollStatsDto> getPayrollStatsByDepartement(Long departementId, Long composanteId, Long zoneId);

    // Kalkile Nombre de payroll par Arrondissement
    List<PayrollStatsDto> getPayrollStatsByArrondissement(Long arrondissementId);

    // Kalkile Nombre de payroll par Commune
    List<PayrollStatsDto> getPayrollStatsByCommune(Long communeId);

    // Kalkile Nombre de payroll par Section Communale
    List<PayrollStatsDto> getPayrollStatsBySectionCommunale(Long sectionId);

    // Kalkile Nombre de payroll par Quartier
    List<PayrollStatsDto> getPayrollStatsByQuartier(Long quartierId);

    // Kalkile Nombre de payroll par Projet
    PayrollStatsDto getPayrollStatsByProjet(String projetId);

    // Kalkile tout estatistik global
    List<PayrollStatsDto> getAllPayrollStats();

    // Kalkile estatistik pou tout composantes
    List<PayrollStatsDto> getAllPayrollStatsByComposantes();

    // Kalkile estatistik pou tout zones
    List<PayrollStatsDto> getAllPayrollStatsByZones();

    // Kalkile estatistik pou tout departements
    List<PayrollStatsDto> getAllPayrollStatsByDepartements();

    // Kalkile estatistik pou tout arrondissements
    List<PayrollStatsDto> getAllPayrollStatsByArrondissements();

    // Kalkile estatistik pou tout communes
    List<PayrollStatsDto> getAllPayrollStatsByCommunes();

    // Kalkile estatistik pou tout sections communales
    List<PayrollStatsDto> getAllPayrollStatsBySectionsCommunales();

    // Kalkile estatistik pou tout quartiers
    List<PayrollStatsDto> getAllPayrollStatsByQuartiers();

    // Kalkile estatistik pou tout projets
    List<PayrollStatsDto> getAllPayrollStatsByProjets();
}
