package com.natha.dev.Controller;

import com.natha.dev.Dto.AddFormationToProjetRequestDto;
import com.natha.dev.Dto.ProjetBeneficiaireFormationDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.IService.ProjetBeneficiaireIService;
import com.natha.dev.Model.ProjetBeneficiaireFormation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjetBeneficiaireFormationController {

    private final ProjetBeneficiaireIService projetBeneficiaireService;

    @Autowired
    public ProjetBeneficiaireFormationController(ProjetBeneficiaireIService projetBeneficiaireService) {
        this.projetBeneficiaireService = projetBeneficiaireService;
    }
//
//    @GetMapping
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('User')")
//    public ResponseEntity<Page<ProjetBeneficiaireFormationDto>> getAllFormationRelationships(Pageable pageable) {
//        return ResponseEntity.ok(projetBeneficiaireService.convertToDtoPage(
//                projetBeneficiaireService.findAllProjetBeneficiaireFormations(pageable)));
//    }
//
//    @GetMapping("/projet/{projetId}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('User')")
//    public ResponseEntity<List<ProjetBeneficiaireFormationDto>> getFormationRelationshipsByProjet(
//            @PathVariable String projetId) {
//        return ResponseEntity.ok(projetBeneficiaireService.convertToDtoList(
//                projetBeneficiaireService.findAllByProjetId(projetId)));
//    }
//
    @GetMapping("/projet/{projetId}/formations")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('User')")
    public ResponseEntity<List<FormationDto>> getFormationsByProjetId(
            @PathVariable String projetId) {
        return ResponseEntity.ok(projetBeneficiaireService.findFormationsByProjetId(projetId));
    }
    
    @PostMapping("/add-formation")
    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
    public ResponseEntity<ProjetBeneficiaireFormationDto> addFormationToProjet(
            @RequestBody AddFormationToProjetRequestDto requestDto) {
        return ResponseEntity.ok(projetBeneficiaireService.addFormationToProjet(requestDto));
    }
//
//    @GetMapping("/{projetBeneficiaireId}/formation/{formationId}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin') or hasRole('User')")
//    public ResponseEntity<ProjetBeneficiaireFormationDto> getFormationRelationship(
//            @PathVariable String projetBeneficiaireId,
//            @PathVariable String formationId) {
//        return projetBeneficiaireService.findProjetBeneficiaireFormation(projetBeneficiaireId, formationId)
//                .map(projetBeneficiaireService::convertToDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
//    public ResponseEntity<ProjetBeneficiaireFormationDto> createFormationRelationship(
//            @RequestBody ProjetBeneficiaireFormationDto dto) {
//        ProjetBeneficiaireFormation created = projetBeneficiaireService.saveProjetBeneficiaireFormation(dto);
//        return ResponseEntity.ok(projetBeneficiaireService.convertToDto(created));
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
//    public ResponseEntity<ProjetBeneficiaireFormationDto> updateFormationRelationship(
//            @PathVariable String id,
//            @RequestBody ProjetBeneficiaireFormationDto dto) {
//        dto.setId(id);
//        ProjetBeneficiaireFormation updated = projetBeneficiaireService.updateProjetBeneficiaireFormation(dto);
//        return ResponseEntity.ok(projetBeneficiaireService.convertToDto(updated));
//    }
//
//    @DeleteMapping("/{projetBeneficiaireId}/formation/{formationId}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
//    public ResponseEntity<Void> deleteFormationRelationship(
//            @PathVariable String projetBeneficiaireId,
//            @PathVariable String formationId) {
//        projetBeneficiaireService.removeFormationFromProjetBeneficiaire(projetBeneficiaireId, formationId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/projet/{projetId}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
//    public ResponseEntity<Void> deleteAllFormationRelationshipsByProjet(@PathVariable String projetId) {
//        projetBeneficiaireService.findAllByProjetId(projetId).stream()
//                .forEach(rel -> projetBeneficiaireService.removeFormationFromProjetBeneficiaire(
//                        rel.getProjetBeneficiaire().getIdProjetBeneficiaire(),
//                        rel.getFormation().getIdFormation()));
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/formation/{formationId}")
//    @PreAuthorize("hasRole('Admin') or hasRole('SuperAdmin')")
//    public ResponseEntity<Void> deleteAllFormationRelationshipsByFormation(@PathVariable String formationId) {
//        projetBeneficiaireService.findAllByFormationId(formationId).stream()
//                .forEach(rel -> projetBeneficiaireService.removeFormationFromProjetBeneficiaire(
//                        rel.getProjetBeneficiaire().getIdProjetBeneficiaire(),
//                        rel.getFormation().getIdFormation()));
//        return ResponseEntity.noContent().build();
//    }
}
