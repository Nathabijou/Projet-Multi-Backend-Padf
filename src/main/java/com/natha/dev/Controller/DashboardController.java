package com.natha.dev.Controller;

import com.natha.dev.Dto.ChapitreStatsDto;
import com.natha.dev.Dto.DashboardFormationDetailsDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.Dto.ModuleStatsDto;
import com.natha.dev.IService.DashboardIService;
import com.natha.dev.IService.FormationIService;
import com.natha.dev.Model.DashboardFilter;
import com.natha.dev.Dto.KpiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api") // ajoute prefix si ou vle
public class DashboardController {

    @Autowired
    private DashboardIService dashboardIService;

    @Autowired
    private FormationIService formationService;

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard")
    public ResponseEntity<KpiResponse> getDashboard(
            @RequestParam(required = false) Long composanteId,
            @RequestParam(required = false) Long zoneId,
            @RequestParam(required = false) Long departementId,
            @RequestParam(required = false) Long arrondissementId,
            @RequestParam(required = false) Long communeId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long quartierId,
            @RequestParam(required = false) String projetId,
            Principal principal) {

        // Kreye yon nouvo filt ak paramèt yo
        DashboardFilter filter = new DashboardFilter();
        filter.setComposanteId(composanteId);
        filter.setZoneId(zoneId);
        filter.setDepartementId(departementId);
        filter.setArrondissementId(arrondissementId);
        filter.setCommuneId(communeId);
        filter.setSectionId(sectionId);
        filter.setQuartierId(quartierId);
        filter.setProjetId(projetId);

        String username = principal != null ? principal.getName() : "anonymous";
        KpiResponse response = dashboardIService.getKpiData(filter, username);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/dashboard")
    public ResponseEntity<KpiResponse> postDashboard(@RequestBody DashboardFilter filter, Principal principal) {
        String username = principal.getName();
        KpiResponse response = dashboardIService.getKpiData(filter, username);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/count-projets")
    public ResponseEntity<Long> countProjets(@ModelAttribute DashboardFilter filter) {
        long count = dashboardIService.countProjets(filter);
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/module-stats")
    public ResponseEntity<List<ModuleStatsDto>> getModuleStats() {
        List<ModuleStatsDto> stats = dashboardIService.getModuleStats();
        return ResponseEntity.ok(stats);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/all-formations")
    public ResponseEntity<List<FormationDto>> getAllFormations() {
        List<FormationDto> formations = formationService.getAll();
        return ResponseEntity.ok(formations);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/check-notes")
    public ResponseEntity<String> checkNotes() {
        try {
            // Rekèt pou wè tout nòt yo ak si yo kalifye oswa pa
            String query = "SELECT " +
                    "b.sexe, " +
                    "pbf.note_finale, " +
                    "CASE WHEN pbf.note_finale >= 70 THEN 'Kalifye' ELSE 'Pa Kalifye' END AS statu, " +
                    "COUNT(DISTINCT pb.beneficiaire_id) AS total " +
                    "FROM projet_beneficiaire_formation pbf " +
                    "JOIN projet_beneficiaire pb ON pbf.projet_beneficiaire_id = pb.projet_beneficiaire_id " +
                    "JOIN beneficiaire b ON pb.beneficiaire_id = b.beneficiaire_id " +
                    "GROUP BY b.sexe, statu, pbf.note_finale " +
                    "ORDER BY b.sexe, statu, pbf.note_finale";

            List<Object[]> results = dashboardIService.executeFormationQuery(query);

            // Konvèti rezilta yo nan yon fòma ki pi lizib
            StringBuilder sb = new StringBuilder();
            sb.append("Sèks\tNòt Final\tStati\tKantite\n");
            sb.append("----------------------------------------\n");

            for (Object[] row : results) {
                String sexe = String.valueOf(row[0]);
                Double note = row[1] != null ? (Double)row[1] : null;
                String statu = String.valueOf(row[2]);
                Long total = ((Number)row[3]).longValue();

                sb.append(String.format("%s\t%s\t%s\t%d\n",
                        sexe,
                        note != null ? String.format("%.2f", note) : "NULL",
                        statu,
                        total));
            }

            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erè: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/formations/test")
    public ResponseEntity<String> testFormationData() {
        try {
            // Teste konbyen benefisyè ki gen nòt final ki pi gran pase 70
            String query = "SELECT pbf.id, pbf.note_finale, b.sexe, b.nom, b.prenom " +
                    "FROM projet_beneficiaire_formation pbf " +
                    "JOIN projet_beneficiaire pb ON pbf.projet_beneficiaire_id = pb.projet_beneficiaire_id " +
                    "JOIN beneficiaire b ON pb.beneficiaire_id = b.beneficiaire_id " +
                    "ORDER BY pbf.note_finale DESC";

            List<Object[]> results = dashboardIService.executeFormationQuery(query);

            // Konvèti rezilta yo nan yon fòma ki pi lizib
            StringBuilder sb = new StringBuilder();
            sb.append("ID\tNote Finale\tSèks\tNon Konplè\n");
            sb.append("----------------------------------------\n");

            for (Object[] row : results) {
                String id = String.valueOf(row[0]);
                String note = row[1] != null ? String.format("%.2f", row[1]) : "NULL";
                String sexe = String.valueOf(row[2]);
                String nom = String.valueOf(row[3]);
                String prenom = String.valueOf(row[4]);

                sb.append(String.format("%s\t%s\t%s\t%s %s\n",
                        id, note, sexe, prenom, nom));
            }

            return ResponseEntity.ok(sb.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erè: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/formations")
    public ResponseEntity<KpiResponse> getFormationStatistics() {
        KpiResponse response = dashboardIService.getFormationStatistics();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/chapitres/stats")
    public ResponseEntity<List<ChapitreStatsDto>> getChapitresStats(
            @RequestParam(required = false) Long composanteId,
            @RequestParam(required = false) Long zoneId,
            @RequestParam(required = false) Long departementId,
            @RequestParam(required = false) Long arrondissementId,
            @RequestParam(required = false) Long communeId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long quartierId,
            @RequestParam(required = false) String projetId
    ) {
        List<ChapitreStatsDto> stats = dashboardIService.getChapitresStats(
                composanteId, zoneId, departementId,
                arrondissementId, communeId, sectionId,
                quartierId, projetId
        );
        return ResponseEntity.ok(stats);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/dashboard/formation-details")
    public ResponseEntity<DashboardFormationDetailsDto> getFormationDetails(
            @RequestParam(required = false) Long composanteId,
            @RequestParam(required = false) Long zoneId,
            @RequestParam(required = false) Long departementId,
            @RequestParam(required = false) Long arrondissementId,
            @RequestParam(required = false) Long communeId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long quartierId,
            @RequestParam(required = false) String projetId,
            Principal principal
    ) {
        String userName = principal.getName();
        DashboardFormationDetailsDto details = dashboardIService.getFormationDetails(
                userName, composanteId, zoneId, departementId, arrondissementId,
                communeId, sectionId, quartierId, projetId);
        return ResponseEntity.ok(details);
    }
}
