package com.natha.dev.Controller;

import com.natha.dev.Dto.PresenceCountDto;
import com.natha.dev.Dto.PresenceDto;
import com.natha.dev.IService.PresenceIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/presences/count/{projetId}/{beneficiaireId}")
    public ResponseEntity<PresenceCountDto> getPresenceCountByPeriode(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debutPeriode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finPeriode) {
        return ResponseEntity.ok(presenceIService.getPresenceCountByPeriode(projetId, beneficiaireId, debutPeriode, finPeriode));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/presences/{idPresence}/checkout")
    public ResponseEntity<PresenceDto> checkOut(
            @PathVariable String idPresence,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureSorti) {
        return ResponseEntity.ok(presenceIService.checkOut(idPresence, heureSorti));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/presences/{idPresence}/reset")
    public ResponseEntity<PresenceDto> resetPresenceTime(
            @PathVariable String idPresence,
            @RequestParam String timeType) {
        return ResponseEntity.ok(presenceIService.resetPresenceTime(idPresence, timeType));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/presences/{idPresence}/checkin")
    public ResponseEntity<PresenceDto> updateCheckIn(
            @PathVariable String idPresence,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heurEntrer) {
        return ResponseEntity.ok(presenceIService.updateCheckIn(idPresence, heurEntrer));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/presences/{idPresence}/checkout/update")
    public ResponseEntity<PresenceDto> updateCheckOut(
            @PathVariable String idPresence,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureSorti) {
        return ResponseEntity.ok(presenceIService.updateCheckOut(idPresence, heureSorti));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @DeleteMapping("/presences/{idPresence}/checkin")
    public ResponseEntity<String> deleteCheckIn(@PathVariable String idPresence) {
        presenceIService.deleteCheckIn(idPresence);
        return ResponseEntity.ok("Check-in efase avèk siksè");
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @DeleteMapping("/presences/{idPresence}/checkout")
    public ResponseEntity<String> deleteCheckOut(@PathVariable String idPresence) {
        presenceIService.deleteCheckOut(idPresence);
        return ResponseEntity.ok("Check-out efase avèk siksè");
    }


}
