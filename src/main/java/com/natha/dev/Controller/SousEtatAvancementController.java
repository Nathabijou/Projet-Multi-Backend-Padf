package com.natha.dev.Controller;

import com.natha.dev.Dto.PhotoSousEtatDto;
import com.natha.dev.Dto.SousEtatAvancementDto;
import com.natha.dev.IService.IPhotoSousEtatService;
import com.natha.dev.IService.ISousEtatAvancementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/sous-etat-avancement")
public class SousEtatAvancementController {

    private final ISousEtatAvancementService sousEtatAvancementService;
    private final IPhotoSousEtatService photoSousEtatService;

    @Autowired
    public SousEtatAvancementController(
            ISousEtatAvancementService sousEtatAvancementService,
            IPhotoSousEtatService photoSousEtatService) {
        this.sousEtatAvancementService = sousEtatAvancementService;
        this.photoSousEtatService = photoSousEtatService;
    }

    @PostMapping
    public ResponseEntity<SousEtatAvancementDto> createSousEtatAvancement(@RequestBody SousEtatAvancementDto sousEtatAvancementDto) {
        SousEtatAvancementDto createdSousEtat = sousEtatAvancementService.createSousEtatAvancement(sousEtatAvancementDto);
        return new ResponseEntity<>(createdSousEtat, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SousEtatAvancementDto> updateSousEtatAvancement(
            @PathVariable String id,
            @RequestBody SousEtatAvancementDto sousEtatAvancementDto) {
        SousEtatAvancementDto updatedSousEtat = sousEtatAvancementService.updateSousEtatAvancement(id, sousEtatAvancementDto);
        return ResponseEntity.ok(updatedSousEtat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSousEtatAvancement(@PathVariable String id) {
        sousEtatAvancementService.deleteSousEtatAvancement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousEtatAvancementDto> getSousEtatAvancementById(@PathVariable String id) {
        SousEtatAvancementDto sousEtatAvancementDto = sousEtatAvancementService.getSousEtatAvancementById(id);
        return ResponseEntity.ok(sousEtatAvancementDto);
    }

    @GetMapping("/etat-avancement/{etatAvancementId}")
    public ResponseEntity<List<SousEtatAvancementDto>> getSousEtatsAvancementByEtatAvancementId(
            @PathVariable String etatAvancementId) {
        List<SousEtatAvancementDto> sousEtats = sousEtatAvancementService
                .getSousEtatsAvancementByEtatAvancementId(etatAvancementId);
        return ResponseEntity.ok(sousEtats);
    }

    @GetMapping("/{id}/pourcentage")
    public ResponseEntity<Double> calculerPourcentageRealise(@PathVariable String id) {
        double pourcentage = sousEtatAvancementService.calculerPourcentageRealise(id);
        return ResponseEntity.ok(pourcentage);
    }

    // Photo endpoints
    @PostMapping("/{sousEtatId}/photos")
    public ResponseEntity<PhotoSousEtatDto> uploadPhoto(
            @PathVariable String sousEtatId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "estPhotoPrincipale", defaultValue = "false") boolean estPhotoPrincipale) {
        
        PhotoSousEtatDto photoDto = photoSousEtatService.uploadAndCreatePhotoSousEtat(
                file, description, estPhotoPrincipale, sousEtatId);
        
        return new ResponseEntity<>(photoDto, HttpStatus.CREATED);
    }

    @PutMapping("/photos/{photoId}")
    public ResponseEntity<PhotoSousEtatDto> updatePhoto(
            @PathVariable String photoId,
            @RequestBody PhotoSousEtatDto photoSousEtatDto) {
        PhotoSousEtatDto updatedPhoto = photoSousEtatService.updatePhotoSousEtat(photoId, photoSousEtatDto);
        return ResponseEntity.ok(updatedPhoto);
    }

    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String photoId) {
        photoSousEtatService.deletePhotoSousEtat(photoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sousEtatId}/photos")
    public ResponseEntity<List<PhotoSousEtatDto>> getPhotosBySousEtatId(@PathVariable String sousEtatId) {
        List<PhotoSousEtatDto> photos = photoSousEtatService.getPhotosBySousEtatAvancementId(sousEtatId);
        return ResponseEntity.ok(photos);
    }

    @PostMapping("/photos/{photoId}/set-main")
    public ResponseEntity<Void> setAsMainPhoto(
            @PathVariable String photoId,
            @RequestParam String sousEtatId) {
        photoSousEtatService.setAsMainPhoto(photoId, sousEtatId);
        return ResponseEntity.ok().build();
    }
}
