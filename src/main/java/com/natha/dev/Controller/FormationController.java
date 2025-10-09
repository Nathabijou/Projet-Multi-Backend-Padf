package com.natha.dev.Controller;

import com.natha.dev.Dto.AddBeneficiaireToFormationRequestDto;
import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.Dto.FormationDto;
import com.natha.dev.Dto.ProjetBeneficiaireFormationDto;
import com.natha.dev.Dto.Request.AddFormationToProjetBeneficiaireRequestDto;
import com.natha.dev.IService.FormationIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    private FormationIService service;


    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countFormationRecords() {
        long count = service.countAllFormationRecords();
        Map<String, Object> response = new HashMap<>();
        response.put("totalFormations", count);
        response.put("message", count > 0 ? "Gen kèk fòmasyon ki jwenn" : "Pa gen okenn fòmasyon anrejistre");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PostMapping
    public ResponseEntity<FormationDto> createFormation(@RequestBody FormationDto dto) {
        FormationDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PutMapping("/{id}")
    public FormationDto update(@PathVariable String id, @RequestBody FormationDto dto) {
        return service.update(id, dto);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/{id}")
    public FormationDto getById(@PathVariable String id) {
        return service.getById(id);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/all")
    public List<FormationDto> getAll() {
        return service.getAll();
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
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
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/{formationId}/projet/{projetId}/beneficiaires")
    public ResponseEntity<List<BeneficiaireDto>> getBeneficiairesByFormationIdAndProjetId(
            @PathVariable String formationId,
            @PathVariable String projetId) {
        System.out.println("Recherche des bénéficiaires pour la formation: " + formationId + " et le projet: " + projetId);
        List<BeneficiaireDto> beneficiaires = service.getBeneficiairesByFormationId(formationId, projetId);
        return ResponseEntity.ok(beneficiaires);
    }

    //Pecupere liste formation dans projet specifique
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/projet/{projetId}")
    public List<FormationDto> getFormationsByProjetId(@PathVariable String projetId) {
        return service.getFormationsByProjetId(projetId);
    }

    @DeleteMapping("/{formationId}/beneficiaires/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    public ResponseEntity<Void> removeBeneficiaireFromFormation(
            @PathVariable String formationId,
            @PathVariable String beneficiaireId) {
        service.removeBeneficiaireFromFormation(beneficiaireId, formationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Ajoute yon fòmasyon nan yon ProjetBeneficiaire espesifik
     * @param requestDto DTO ki genyen ID fòmasyon an, ID benefisyè a, ak ID pwojè a
     * @return Detay asosyasyon an kreye a
     */
    @PostMapping("/projet-beneficiaire/add-formation")
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    public ResponseEntity<ProjetBeneficiaireFormationDto> addFormationToProjetBeneficiaire(
            @Valid @RequestBody AddFormationToProjetBeneficiaireRequestDto requestDto) {
        ProjetBeneficiaireFormationDto result = service.addFormationToProjetBeneficiaire(requestDto);
        return ResponseEntity.ok(result);
    }
}
