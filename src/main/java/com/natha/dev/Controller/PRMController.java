package com.natha.dev.Controller;

import com.natha.dev.Dto.PRMAfaireDTO;
import com.natha.dev.IService.IPRMService;
import com.natha.dev.Model.PRMRequis;
import com.natha.dev.Model.PRMRealise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prm")
public class PRMController {

    private final IPRMService prmService;

    @Autowired
    public PRMController(IPRMService prmService) {
        this.prmService = prmService;
    }

    // Endpoint pou kreye yon nouvo PRMRequis

    @PostMapping("/requis")
    public ResponseEntity<PRMRequis> createRequis(@RequestBody PRMRequis prmRequis) {
        PRMRequis savedRequis = prmService.saveRequis(prmRequis);
        return ResponseEntity.ok(savedRequis);
    }

    // Endpoint pou jwenn tout PRMRequis yo
    @GetMapping("/requis")
    public ResponseEntity<List<PRMRequis>> getAllRequis() {
        List<PRMRequis> requisList = prmService.getAllRequis();
        return ResponseEntity.ok(requisList);
    }

    // Endpoint pou jwenn yon PRMRequis pa ID
    @GetMapping("/requis/{id}")
    public ResponseEntity<PRMRequis> getRequisById(@PathVariable Long id) {
        PRMRequis requis = prmService.getRequisById(id);
        return ResponseEntity.ok(requis);
    }

    // Endpoint pou efase yon PRMRequis
    @DeleteMapping("/requis/{id}")
    public ResponseEntity<Void> deleteRequis(@PathVariable Long id) {
        prmService.deleteRequisById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pou kreye yon nouvo PRMRealise
    @PostMapping("/realise")
    public ResponseEntity<PRMRealise> createRealise(@RequestBody PRMRealise prmRealise) {
        PRMRealise savedRealise = prmService.saveRealise(prmRealise);
        return ResponseEntity.ok(savedRealise);
    }

    // Endpoint pou jwenn tout PRMRealise yo
    @GetMapping("/realise")
    public ResponseEntity<List<PRMRealise>> getAllRealise() {
        List<PRMRealise> realiseList = prmService.getAllRealise();
        return ResponseEntity.ok(realiseList);
    }

    // Endpoint pou jwenn yon PRMRealise pa ID
    @GetMapping("/realise/{id}")
    public ResponseEntity<PRMRealise> getRealiseById(@PathVariable Long id) {
        PRMRealise realise = prmService.getRealiseById(id);
        return ResponseEntity.ok(realise);
    }

    // Endpoint pou efase yon PRMRealise
    @DeleteMapping("/realise/{id}")
    public ResponseEntity<Void> deleteRealise(@PathVariable Long id) {
        prmService.deleteRealiseById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pou kalkile PRMAfaire
    @GetMapping("/afaire")
    public ResponseEntity<?> getAFaire(
            @RequestParam(required = false) String projetId,
            @RequestParam(required = false) Long quartierId) {

        // Si youn nan paramèt yo manke, retounen yon mesaj erè
        if (projetId == null || quartierId == null) {
            return ResponseEntity.badRequest().body("Paramèt 'projetId' ak 'quartierId' yo obligatwa.");
        }

        Optional<PRMAfaireDTO> afaireOpt = prmService.getAFaire(projetId, quartierId);

        // Si pa gen okenn PRMRequis pou pwojè ak katye sa yo, retounen yon repons 404
        if (afaireOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Retounen PRMAfaireDTO a
        return ResponseEntity.ok(afaireOpt.get());
    }

    // Endpoint pou jwenn PRMRequis pa pwojè ak katye
    @GetMapping("/requis/par-projet-quartier")
    public ResponseEntity<List<PRMRequis>> getRequisByProjetAndQuartier(
            @RequestParam String projetId,
            @RequestParam Long quartierId) {

        List<PRMRequis> requisList = prmService.getRequisByProjetIdAndQuartierId(projetId, quartierId);
        return ResponseEntity.ok(requisList);
    }

    // Endpoint pou jwenn PRMRealise pa pwojè ak katye
    @GetMapping("/realise/par-projet-quartier")
    public ResponseEntity<List<PRMRealise>> getRealiseByProjetAndQuartier(
            @RequestParam String projetId,
            @RequestParam Long quartierId) {

        List<PRMRealise> realiseList = prmService.getRealiseByProjetIdAndQuartierId(projetId, quartierId);
        return ResponseEntity.ok(realiseList);
    }
}
