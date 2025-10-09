package com.natha.dev.Controller;

import com.natha.dev.Dto.EtatAvancementDto;
import com.natha.dev.IService.IEtatAvancementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etat-avancement")
public class EtatAvancementController {

    private final IEtatAvancementService etatAvancementService;

    @Autowired
    public EtatAvancementController(IEtatAvancementService etatAvancementService) {
        this.etatAvancementService = etatAvancementService;
    }

    @PostMapping
    public ResponseEntity<EtatAvancementDto> createEtatAvancement(@RequestBody EtatAvancementDto etatAvancementDto) {
        EtatAvancementDto createdEtat = etatAvancementService.createEtatAvancement(etatAvancementDto);
        return new ResponseEntity<>(createdEtat, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtatAvancementDto> updateEtatAvancement(
            @PathVariable String id,
            @RequestBody EtatAvancementDto etatAvancementDto) {
        EtatAvancementDto updatedEtat = etatAvancementService.updateEtatAvancement(id, etatAvancementDto);
        return ResponseEntity.ok(updatedEtat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtatAvancement(@PathVariable String id) {
        etatAvancementService.deleteEtatAvancement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtatAvancementDto> getEtatAvancementById(@PathVariable String id) {
        EtatAvancementDto etatAvancementDto = etatAvancementService.getEtatAvancementById(id);
        return ResponseEntity.ok(etatAvancementDto);
    }

    @GetMapping
    public ResponseEntity<List<EtatAvancementDto>> getAllEtatsAvancement() {
        List<EtatAvancementDto> etats = etatAvancementService.getAllEtatsAvancement();
        return ResponseEntity.ok(etats);
    }

    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<EtatAvancementDto>> getEtatsAvancementByProjetId(@PathVariable String projetId) {
        List<EtatAvancementDto> etats = etatAvancementService.getEtatsAvancementByProjetId(projetId);
        return ResponseEntity.ok(etats);
    }

    @GetMapping("/projet/{projetId}/pourcentage-total")
    public ResponseEntity<Double> getPourcentageTotalProjet(@PathVariable String projetId) {
        double pourcentage = etatAvancementService.calculerPourcentageTotalProjet(projetId);
        return ResponseEntity.ok(pourcentage);
    }
}
