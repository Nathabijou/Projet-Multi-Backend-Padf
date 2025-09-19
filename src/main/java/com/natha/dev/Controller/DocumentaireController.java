package com.natha.dev.Controller;

import com.natha.dev.Dto.DocumentaireDto;
import com.natha.dev.IService.DocumentaireIService;
import com.natha.dev.Model.Documentaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documentaires")
public class DocumentaireController {

    private final DocumentaireIService documentaireService;

    @Autowired
    public DocumentaireController(DocumentaireIService documentaireService) {
        this.documentaireService = documentaireService;
    }

    /**
     * Telechaje yon nouvo dokiman pou yon pwojè
     */
    @PostMapping(value = "/projets/{projetId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentaireDto> uploadDocumentaire(
            @PathVariable String projetId,
            @RequestParam("file") MultipartFile file,
            @RequestParam Documentaire.DocumentaireType type,
            @RequestParam(required = false) String description) throws IOException {
        
        DocumentaireDto documentaire = documentaireService.uploadDocumentaire(projetId, file, type, description);
        return new ResponseEntity<>(documentaire, HttpStatus.CREATED);
    }

    /**
     * Jwenn yon dokiman pa ID li
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentaireDto> getDocumentaireById(@PathVariable Long id) {
        DocumentaireDto documentaire = documentaireService.getDocumentaireById(id);
        return ResponseEntity.ok(documentaire);
    }

    /**
     * Jwenn tout dokiman yon pwojè espesifik
     */
    @GetMapping("/projets/{projetId}")
    public ResponseEntity<List<DocumentaireDto>> getDocumentairesByProjet(@PathVariable String projetId) {
        List<DocumentaireDto> documentaires = documentaireService.getDocumentairesByProjet(projetId);
        return ResponseEntity.ok(documentaires);
    }

    /**
     * Jwenn tout dokiman yon pwojè espesifik pa kalite dokiman
     * Kalite dokiman posib: FACTURE, DEVIS, CONTRAT, RAPPORT, AUTRE
     */
    @GetMapping("/projets/{projetId}/type/{type}")
    public ResponseEntity<List<DocumentaireDto>> getDocumentairesByProjetAndDocumentType(
            @PathVariable String projetId,
            @PathVariable("type") String typeStr) {
        
        try {
            Documentaire.DocumentaireType type = Documentaire.DocumentaireType.valueOf(typeStr.toUpperCase());
            List<DocumentaireDto> documentaires = documentaireService.getDocumentairesByProjetAndType(projetId, type);
            return ResponseEntity.ok(documentaires);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tip dokiman an pa valab. Tip posib yo se: " + 
                String.join(", ", Documentaire.DocumentaireType.getNames()));
        }
    }
    
    /**
     * Jwenn tout dokiman yon pwojè espesifik pa kalite foto
     * Kalite foto posib: AVANT, PENDANT, APRES, TEMOIGNAGE
     * 
     * @deprecated Itilize /projets/{projetId}/type/{type} ak yon tip dokiman valab olye
     */
    @Deprecated
    @GetMapping("/projets/{projetId}/photo-type/{photoType}")
    public ResponseEntity<List<DocumentaireDto>> getDocumentairesByProjetAndPhotoType(
            @PathVariable String projetId,
            @PathVariable String photoType) {
        
        throw new UnsupportedOperationException("Fonksyonalite sa a pa sipòte ankò. Itilize /projets/" + projetId + 
            "/type/ ak youn nan tip sa yo: " + String.join(", ", Documentaire.DocumentaireType.getNames()));
    }

    /**
     * Mete ajou deskripsyon yon dokiman
     */
    @PutMapping("/{id}/description")
    public ResponseEntity<DocumentaireDto> updateDocumentaireDescription(
            @PathVariable Long id,
            @RequestParam String description) {
        
        DocumentaireDto updatedDocumentaire = documentaireService.updateDocumentaireDescription(id, description);
        return ResponseEntity.ok(updatedDocumentaire);
    }

    /**
     * Efase yon dokiman
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentaire(@PathVariable Long id) {
        documentaireService.deleteDocumentaire(id);
        return ResponseEntity.noContent().build();
    }
}
