package com.natha.dev.Controller;

import com.natha.dev.Dto.AddBeneficiaireToFormationRequestDto;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.Dto.ProjetBeneficiaireFormationDto;
import com.natha.dev.IService.FormationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationIService service;


//    @PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','MANAGER','USER')")
    @PostMapping
    public ResponseEntity<FormationDto> createFormation(@RequestBody FormationDto dto) {
        FormationDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public FormationDto update(@PathVariable String id, @RequestBody FormationDto dto) {
        return service.update(id, dto);
    }

    @GetMapping("/{id}")
    public FormationDto getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/all")
    public List<FormationDto> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @PostMapping("/formations/{formationId}/projets/{projetId}/beneficiaires")
    public ResponseEntity<ProjetBeneficiaireFormationDto> addBeneficiaireToFormation(
            @PathVariable String formationId,
            @PathVariable String projetId,
            @RequestBody AddBeneficiaireToFormationRequestDto requestDto) {
        // Asire ke formationId nan chemen an menm ak sa nan kò a
        requestDto.setIdFormation(formationId);
        requestDto.setIdProjet(projetId);
        ProjetBeneficiaireFormationDto result = service.addBeneficiaireToFormation(requestDto);
        return ResponseEntity.ok(result);
    }
   // Recupere lis beneficiaire nan yon formation pou yon pwojè espesifik
    @PreAuthorize("permitAll()")
    @GetMapping("/{formationId}/projet/{projetId}/beneficiaires")
    public ResponseEntity<List<BeneficiaireDto>> getBeneficiairesByFormationIdAndProjetId(
            @PathVariable String formationId,
            @PathVariable String projetId) {
        System.out.println("Recherche des bénéficiaires pour la formation: " + formationId + " et le projet: " + projetId);
        List<BeneficiaireDto> beneficiaires = service.getBeneficiairesByFormationId(formationId, projetId);
        return ResponseEntity.ok(beneficiaires);
    }

    //Pecupere liste formation dans projet specifique
    @GetMapping("/projet/{projetId}")
    public List<FormationDto> getFormationsByProjetId(@PathVariable String projetId) {
        return service.getFormationsByProjetId(projetId);
    }
    
    @DeleteMapping("/{formationId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<Void> removeBeneficiaireFromFormation(
            @PathVariable String formationId,
            @PathVariable String beneficiaireId) {
        service.removeBeneficiaireFromFormation(beneficiaireId, formationId);
        return ResponseEntity.noContent().build();
    }
}
