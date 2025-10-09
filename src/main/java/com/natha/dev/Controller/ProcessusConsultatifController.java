package com.natha.dev.Controller;

import com.natha.dev.Dto.ProcessusConsultatifDTO;
import com.natha.dev.IService.IProcessusConsultatifService;
import com.natha.dev.Model.ProcessusConsultatif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/processus-consultatif")
public class ProcessusConsultatifController {

    @Autowired
    private IProcessusConsultatifService processusConsultatifService;

    @GetMapping
    public ResponseEntity<List<ProcessusConsultatif>> getAllProcessusConsultatifs() {
        List<ProcessusConsultatif> processus = processusConsultatifService.getAllProcessusConsultatifs();
        return new ResponseEntity<>(processus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessusConsultatif> getProcessusConsultatifById(@PathVariable Long id) {
        ProcessusConsultatif processus = processusConsultatifService.getProcessusConsultatifById(id);
        return new ResponseEntity<>(processus, HttpStatus.OK);
    }

    @GetMapping("/commune/{communeId}")
    public ResponseEntity<List<ProcessusConsultatif>> getProcessusByCommune(@PathVariable Long communeId) {
        List<ProcessusConsultatif> processus = processusConsultatifService.getProcessusConsultatifsByCommune(communeId);
        return new ResponseEntity<>(processus, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProcessusConsultatif> createProcessusConsultatif(@RequestBody ProcessusConsultatifDTO processusDTO) {
        ProcessusConsultatif newProcessus = processusConsultatifService.createProcessusConsultatif(processusDTO);
        return new ResponseEntity<>(newProcessus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessusConsultatif> updateProcessusConsultatif(
            @PathVariable Long id, 
            @RequestBody ProcessusConsultatifDTO processusDTO) {
        ProcessusConsultatif updatedProcessus = processusConsultatifService.updateProcessusConsultatif(id, processusDTO);
        return new ResponseEntity<>(updatedProcessus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessusConsultatif(@PathVariable Long id) {
        processusConsultatifService.deleteProcessusConsultatif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
