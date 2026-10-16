package com.natha.dev.Controller;

import com.natha.dev.Dto.PayrollStatsDto;
import com.natha.dev.IService.PayrollStatsIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PayrollStatsController {

    @Autowired
    private PayrollStatsIService payrollStatsIService;

    // Endpoint pou kalkile estatistik payroll pa Composante
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/composante/{composanteId}")
    public ResponseEntity<PayrollStatsDto> getPayrollStatsByComposante(@PathVariable Long composanteId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByComposante(composanteId));
    }

    // Endpoint pou kalkile estatistik payroll pa Zone
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/zone/{zoneId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsByZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByZone(zoneId));
    }

    // Endpoint pou kalkile estatistik payroll pa Departement
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/departement/{departementId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsByDepartement(@PathVariable Long departementId, @RequestParam(required = false) Long composanteId, @RequestParam(required = false) Long zoneId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByDepartement(departementId, composanteId, zoneId));
    }

    // Endpoint pou kalkile estatistik payroll pa Arrondissement
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/arrondissement/{arrondissementId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsByArrondissement(@PathVariable Long arrondissementId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByArrondissement(arrondissementId));
    }

    // Endpoint pou kalkile estatistik payroll pa Commune
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/commune/{communeId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsByCommune(@PathVariable Long communeId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByCommune(communeId));
    }

    // Endpoint pou kalkile estatistik payroll pa Section Communale
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/section-communale/{sectionId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsBySectionCommunale(@PathVariable Long sectionId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsBySectionCommunale(sectionId));
    }

    // Endpoint pou kalkile estatistik payroll pa Quartier
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/quartier/{quartierId}")
    public ResponseEntity<List<PayrollStatsDto>> getPayrollStatsByQuartier(@PathVariable Long quartierId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByQuartier(quartierId));
    }

    // Endpoint pou kalkile estatistik payroll pa Projet
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/projet/{projetId}")
    public ResponseEntity<PayrollStatsDto> getPayrollStatsByProjet(@PathVariable String projetId) {
        return ResponseEntity.ok(payrollStatsIService.getPayrollStatsByProjet(projetId));
    }

    // Endpoint pou kalkile tout estatistik global
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/global")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStats() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStats());
    }

    // Endpoint pou kalkile estatistik pou tout composantes
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/composantes")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByComposantes() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByComposantes());
    }

    // Endpoint pou kalkile estatistik pou tout zones
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/zones")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByZones() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByZones());
    }

    // Endpoint pou kalkile estatistik pou tout departements
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/departements")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByDepartements() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByDepartements());
    }

    // Endpoint pou kalkile estatistik pou tout arrondissements
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/arrondissements")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByArrondissements() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByArrondissements());
    }

    // Endpoint pou kalkile estatistik pou tout communes
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/communes")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByCommunes() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByCommunes());
    }

    // Endpoint pou kalkile estatistik pou tout sections communales
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/sections-communales")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsBySectionsCommunales() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsBySectionsCommunales());
    }

    // Endpoint pou kalkile estatistik pou tout quartiers
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/quartiers")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByQuartiers() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByQuartiers());
    }

    // Endpoint pou kalkile estatistik pou tout projets
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/payroll-stats/all/projets")
    public ResponseEntity<List<PayrollStatsDto>> getAllPayrollStatsByProjets() {
        return ResponseEntity.ok(payrollStatsIService.getAllPayrollStatsByProjets());
    }
}
