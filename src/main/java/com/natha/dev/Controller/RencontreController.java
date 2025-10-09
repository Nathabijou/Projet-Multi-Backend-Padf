package com.natha.dev.Controller;

import com.natha.dev.Dto.RencontreDto;
import com.natha.dev.IService.IRencontreService;
import com.natha.dev.Model.Rencontre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rencontres")
@CrossOrigin(origins = "*")
public class RencontreController {

    @Autowired
    private IRencontreService rencontreService;

    @GetMapping
    public ResponseEntity<List<Rencontre>> getAllRencontres() {
        return new ResponseEntity<>(rencontreService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rencontre> getRencontreById(@PathVariable Long id) {
        return new ResponseEntity<>(rencontreService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/processus-consultatif/{processusConsultatifId}")
    public ResponseEntity<List<Rencontre>> getRencontresByProcessusConsultatifId(@PathVariable Long processusConsultatifId) {
        return new ResponseEntity<>(rencontreService.findByProcessusConsultatifId(processusConsultatifId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Rencontre> createRencontre(@RequestBody RencontreDto rencontreDto) {
        return new ResponseEntity<>(rencontreService.save(rencontreDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rencontre> updateRencontre(@PathVariable Long id, @RequestBody RencontreDto rencontreDto) {
        return new ResponseEntity<>(rencontreService.update(id, rencontreDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRencontre(@PathVariable Long id) {
        rencontreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{rencontreId}/participants/{personneId}")
    public ResponseEntity<Rencontre> addParticipantToRencontre(
            @PathVariable Long rencontreId,
            @PathVariable Long personneId) {
        return new ResponseEntity<>(
            rencontreService.addParticipantToRencontre(rencontreId, personneId),
            HttpStatus.OK
        );
    }
    
    @DeleteMapping("/{rencontreId}/participants/{personneId}")
    public ResponseEntity<Rencontre> removeParticipantFromRencontre(
            @PathVariable Long rencontreId,
            @PathVariable Long personneId) {
        return new ResponseEntity<>(
            rencontreService.removeParticipantFromRencontre(rencontreId, personneId),
            HttpStatus.OK
        );
    }
}
