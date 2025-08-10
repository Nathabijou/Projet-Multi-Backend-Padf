package com.natha.dev.Controller;

import com.natha.dev.Dto.PresenceDto;
import com.natha.dev.IService.PresenceIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class PresenceController {

    @Autowired
    private PresenceIService presenceIService;

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/presencesss/{projetId}/{beneficiaireId}")
    public ResponseEntity<PresenceDto> ajouterPresence(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody PresenceDto dto) {
        return ResponseEntity.ok(presenceIService.ajouterPresence(projetId, beneficiaireId, dto));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/presencess/{projetId}/{beneficiaireId}")
    public ResponseEntity<List<PresenceDto>> getPresences(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return ResponseEntity.ok(presenceIService.getPresencesByProjetBeneficiaire(projetId, beneficiaireId));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/presences/{idPresence}")
    public ResponseEntity<PresenceDto> modifierPresence(
            @PathVariable String idPresence,
            @RequestBody PresenceDto dto) {
        return ResponseEntity.ok(presenceIService.modifierPresence(idPresence, dto));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @DeleteMapping("/presence/{idPresence}")
    public ResponseEntity<String> supprimerPresence(@PathVariable String idPresence) {
        presenceIService.supprimerPresence(idPresence);
        return ResponseEntity.ok("Presence supprime avèk siksè");
    }




}
