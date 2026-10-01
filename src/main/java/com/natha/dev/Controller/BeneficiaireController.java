package com.natha.dev.Controller;

import com.natha.dev.Dto.BeneficiaireDto;
import com.natha.dev.IService.BeneficiaireIService;
import com.natha.dev.IService.ComposanteBeneficiaireIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BeneficiaireController {

    @Autowired
    private BeneficiaireIService beneficiaireIService;

    @Autowired
    private ComposanteBeneficiaireIService composanteBeneficiaireIService;
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/beneficiaires/type/{typeBeneficiaire}")
    public ResponseEntity<List<BeneficiaireDto>> getBeneficiairesByType(@PathVariable String typeBeneficiaire) {
        List<BeneficiaireDto> beneficiaires = beneficiaireIService.findAllByTypeBeneficiaire(typeBeneficiaire);
        return ResponseEntity.ok(beneficiaires);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/beneficiaires/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> getBeneficiaireById(@PathVariable String beneficiaireId) {
        return beneficiaireIService.findById(beneficiaireId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/beneficiaires/create")
    public ResponseEntity<BeneficiaireDto> create(@RequestBody BeneficiaireDto dto) {
        return ResponseEntity.ok(beneficiaireIService.save(dto));
    }

    // Endpoint espesifik pou kreye yon kandidat
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/beneficiaires/candidats/create")
    public ResponseEntity<BeneficiaireDto> createCandidat(@RequestBody BeneficiaireDto dto) {
        dto.setTypeBeneficiaire("CANDIDAT");
        return ResponseEntity.ok(beneficiaireIService.save(dto));
    }

    // Endpoint pou rekipere tout kandida yo
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/beneficiaires/candidats")
    public ResponseEntity<List<BeneficiaireDto>> getAllCandidats() {
        List<BeneficiaireDto> candidats = beneficiaireIService.findAllByTypeBeneficiaire("CANDIDAT");
        return ResponseEntity.ok(candidats);
    }

     //Add Beneficiaire to project (Yes Verify)
     @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PostMapping("/beneficiaires/projets/{idProjet}/beneficiaires")
    public ResponseEntity<BeneficiaireDto> createBeneficiaireInProjet(
            @RequestBody BeneficiaireDto beneficiaireDto,
            @PathVariable String idProjet) {

        BeneficiaireDto createdBeneficiaire = beneficiaireIService.creerBeneficiaireEtAssocierAuProjet(beneficiaireDto, idProjet);
        return new ResponseEntity<>(createdBeneficiaire, HttpStatus.CREATED);
    }

    //Modify benerifiaire with project (yes Verify)
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PutMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> updateBeneficiaireDansProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId,
            @RequestBody BeneficiaireDto dto) {
        BeneficiaireDto updated = beneficiaireIService.updateBeneficiaireDansProjet(projetId, beneficiaireId, dto);
        return ResponseEntity.ok(updated);
    }

    //get beneficiaire with id (yes verify)
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<BeneficiaireDto> getBeneficiaireInProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        return beneficiaireIService.findBeneficiaireInProjet(projetId, beneficiaireId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //Transfer Beneficiares with anoter project
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PutMapping("/beneficiaires/beneficiaires/{beneficiaireId}/transfer/{ancienProjetId}/{nouveauProjetId}")
    public ResponseEntity<String> transfererBeneficiaire(
            @PathVariable String beneficiaireId,
            @PathVariable String ancienProjetId,
            @PathVariable String nouveauProjetId) {
        beneficiaireIService.transfererBeneficiaireDansProjet(beneficiaireId, ancienProjetId, nouveauProjetId);
        return ResponseEntity.ok("Beneficiaire transfere soti nan pwojè " + ancienProjetId + " pou ale nan pwojè " + nouveauProjetId);
    }
    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping("/beneficiaires/projets/{projetId}/beneficiaires/{beneficiaireId}")
    public ResponseEntity<String> deleteBeneficiaireFromProjet(
            @PathVariable String projetId,
            @PathVariable String beneficiaireId) {
        beneficiaireIService.deleteBeneficiaireFromProjet(beneficiaireId, projetId);
        return ResponseEntity.ok("Beneficiaire efase nan pwojè avèk siksè");
    }

    //Transferer un beneficiaire dans formation
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PostMapping("/beneficiaires/beneficiaires/{idBeneficiaire}/formation/{idProjet}/{idFormation}")
    public ResponseEntity<?> ajouterBeneficiaireDansFormation(
            @PathVariable String idBeneficiaire,
            @PathVariable String idProjet,
            @PathVariable String idFormation) {
        beneficiaireIService.ajouterBeneficiaireDansFormation(idBeneficiaire, idProjet, idFormation);
        return ResponseEntity.ok("Successfully");
    }

    // Endpoint pou enregistre yon kandidat nan yon composante
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @PostMapping("/beneficiaires/composantes/{composanteId}/candidats/{beneficiaireId}")
    public ResponseEntity<String> enregistrerCandidatDansComposante(
            @PathVariable Long composanteId,
            @PathVariable String beneficiaireId) {
        composanteBeneficiaireIService.save(composanteId, beneficiaireId);
        return ResponseEntity.ok("Candidat enregistre nan composante avèk siksè");
    }

    // Endpoint pou jwenn lis kandidat nan yon composante
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/beneficiaires/composantes/{composanteId}/candidats")
    public ResponseEntity<List<BeneficiaireDto>> getCandidatsByComposante(@PathVariable Long composanteId) {
        List<BeneficiaireDto> candidats = composanteBeneficiaireIService.getCandidatsByComposante(composanteId);
        return ResponseEntity.ok(candidats);
    }

}

