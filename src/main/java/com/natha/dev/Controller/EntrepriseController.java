package com.natha.dev.Controller;

import com.natha.dev.Dto.EntrepriseDto;
import com.natha.dev.IService.IEntrepriseService;
import com.natha.dev.Model.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EntrepriseController {

    private final IEntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(IEntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @GetMapping("/api/entreprises")
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        return ResponseEntity.ok(entrepriseService.findAll());
    }

    @GetMapping("/api/entreprises/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Long id) {
        return ResponseEntity.ok(entrepriseService.findById(id));
    }

    @PostMapping("/api/entreprises")
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody EntrepriseDto entrepriseDto) {
        return ResponseEntity.ok(entrepriseService.create(entrepriseDto));
    }

    @PutMapping("/api/entreprises/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(
            @PathVariable Long id, 
            @RequestBody EntrepriseDto entrepriseDto) {
        return ResponseEntity.ok(entrepriseService.update(id, entrepriseDto));
    }

    @DeleteMapping("/api/entreprises/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEntreprise(@PathVariable Long id) {
        entrepriseService.delete(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("/api/entreprises/deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
