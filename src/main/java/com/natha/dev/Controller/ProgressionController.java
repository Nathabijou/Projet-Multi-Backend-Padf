package com.natha.dev.Controller;

import com.natha.dev.Dto.ProgressionDTO;
import com.natha.dev.IService.ProgressionIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProgressionController {

    @Autowired
    private ProgressionIService progressionIService;

    // Create Progression
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/progressions")
    public ResponseEntity<ProgressionDTO> createProgression(@RequestBody ProgressionDTO progressionDTO) {
        ProgressionDTO created = progressionIService.save(progressionDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Get All Progressions
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/progressions")
    public ResponseEntity<List<ProgressionDTO>> getAllProgressions() {
        return ResponseEntity.ok(progressionIService.findAll());
    }

    // Get Progression by ID
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/progressions/{idProgression}")
    public ResponseEntity<ProgressionDTO> getProgressionById(@PathVariable String idProgression) {
        return progressionIService.findById(idProgression)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get Progressions by Projet ID
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User', 'Moderant')")
    @GetMapping("/progressions/projet/{projetId}")
    public ResponseEntity<List<ProgressionDTO>> getProgressionsByProjetId(@PathVariable String projetId) {
        return ResponseEntity.ok(progressionIService.findByProjetId(projetId));
    }

    // Update Progression
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/progressions/{idProgression}")
    public ResponseEntity<ProgressionDTO> updateProgression(
            @PathVariable String idProgression,
            @RequestBody ProgressionDTO progressionDTO) {
        ProgressionDTO updated = progressionIService.update(idProgression, progressionDTO);
        return ResponseEntity.ok(updated);
    }

    // Delete Progression
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/progressions/{idProgression}")
    public ResponseEntity<Void> deleteProgression(@PathVariable String idProgression) {
        progressionIService.deleteById(idProgression);
        return ResponseEntity.noContent().build();
    }
}
