package com.natha.dev.Controller;

import com.natha.dev.Dto.PersonneDto;
import com.natha.dev.IService.IPersonneService;
import com.natha.dev.Model.Personne;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnes")
@CrossOrigin(origins = "*")
public class PersonneController {

    @Autowired
    private IPersonneService personneService;

    @GetMapping
    public ResponseEntity<List<Personne>> getAllPersonnes() {
        return new ResponseEntity<>(personneService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonneById(@PathVariable Long id) {
        try {
            Personne personne = personneService.findById(id);
            return new ResponseEntity<>(personne, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rencontre/{rencontreId}")
    public ResponseEntity<List<Personne>> getPersonnesByRencontreId(@PathVariable Long rencontreId) {
        return new ResponseEntity<>(personneService.findByRencontreId(rencontreId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Personne> createPersonne(@RequestBody PersonneDto personneDto) {
        return new ResponseEntity<>(personneService.save(personneDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(
            @PathVariable Long id,
            @RequestBody PersonneDto personneDto) {
        return new ResponseEntity<>(personneService.update(id, personneDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonne(@PathVariable Long id) {
        personneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
