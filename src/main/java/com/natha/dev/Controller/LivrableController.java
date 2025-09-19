package com.natha.dev.Controller;

import com.natha.dev.Dto.LivrableDto;
import com.natha.dev.IService.ILivrableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LivrableController {

    @Autowired
    private ILivrableService livrableService;

    @PostMapping("/api/livrables")
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    public ResponseEntity<LivrableDto> createLivrable(
            @RequestBody LivrableDto livrableDto,
            Authentication authentication) {
        String username = authentication.getName();
        LivrableDto createdLivrable = livrableService.createLivrable(livrableDto, username);
        return ResponseEntity.ok(createdLivrable);
    }

    @GetMapping("/api/livrables/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    public ResponseEntity<LivrableDto> getLivrableById(@PathVariable Long id) {
        LivrableDto livrable = livrableService.getLivrableById(id);
        return ResponseEntity.ok(livrable);
    }

    @GetMapping("/api/livrables/composante/{composanteId}")
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    public ResponseEntity<List<LivrableDto>> getLivrablesByComposante(
            @PathVariable Long composanteId) {
        List<LivrableDto> livrables = livrableService.getLivrablesByComposante(composanteId);
        return ResponseEntity.ok(livrables);
    }

    @PutMapping("/api/livrables/{id}")
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    public ResponseEntity<LivrableDto> updateLivrable(
            @PathVariable Long id,
            @RequestBody LivrableDto livrableDto,
            Authentication authentication) {
        String username = authentication.getName();
        LivrableDto updatedLivrable = livrableService.updateLivrable(id, livrableDto, username);
        return ResponseEntity.ok(updatedLivrable);
    }

    @DeleteMapping("/api/livrables/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteLivrable(@PathVariable Long id) {
        livrableService.deleteLivrable(id);
        return ResponseEntity.noContent().build();
    }
}
