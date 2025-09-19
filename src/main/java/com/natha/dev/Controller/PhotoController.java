package com.natha.dev.Controller;

import com.natha.dev.Dto.PhotoDTO;
import com.natha.dev.IService.PhotoIService;
import com.natha.dev.Model.Photo.PhotoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private PhotoIService photoIService;

    @Autowired
    public PhotoController(PhotoIService photoIService) {
        this.photoIService = photoIService;
    }

    /**
     * Telechaje yon nouvo foto pou yon pwojè
     *
     * @param projetId ID pwojè a
     * @param file Fichye foto a pou telechaje
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Repons HTTP ki gen detay foto ki fèk kreye a
     */
    @PostMapping("/upload")
    public ResponseEntity<PhotoDTO> uploadPhoto(
            @RequestParam String projetId,
            @RequestParam("file") MultipartFile file,
            @RequestParam PhotoType type) {

        try {
            PhotoDTO photoDTO = photoIService.uploadPhoto(projetId, file, type, null);
            return new ResponseEntity<>(photoDTO, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Jwenn tout foto yon pwojè espesifik
     *
     * @param projetId ID pwojè a
     * @return Yon lis tout foto pwojè a
     */
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByProjet(@PathVariable String projetId) {
        List<PhotoDTO> photos = photoIService.getPhotosByProjet(projetId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    /**
     * Jwenn tout foto yon pwojè espesifik selon kalite foto a
     *
     * @param projetId ID pwojè a
     * @param type Kalite foto a (AVANT, PENDANT, APRES)
     * @return Yon lis foto ki matche ak kritè yo
     */
    @GetMapping("/projet/{projetId}/type/{type}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByProjetAndType(
            @PathVariable String projetId,
            @PathVariable PhotoType type) {

        List<PhotoDTO> photos = photoIService.getPhotosByProjetAndType(projetId, type);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    /**
     * Jwenn yon foto pa ID li
     *
     * @param id ID foto a
     * @return Detay foto a si li egziste
     */
    @GetMapping("/{id}")
    public ResponseEntity<PhotoDTO> getPhotoById(@PathVariable Long id) {
        PhotoDTO photoDTO = photoIService.getPhotoById(id);
        if (photoDTO != null) {
            return new ResponseEntity<>(photoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Mete ajou deskripsyon yon foto
     *
     * @param id ID foto a pou mete ajou
     * @param description Nouvo deskripsyon an
     * @return Detay foto a ak deskripsyon an mete ajou
     */
    @PutMapping("/{id}/description")
    public ResponseEntity<PhotoDTO> updatePhotoDescription(
            @PathVariable Long id,
            @RequestParam String description) {

        try {
            PhotoDTO updatedPhoto = photoIService.updatePhotoDescription(id, description);
            return new ResponseEntity<>(updatedPhoto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Efase yon foto
     *
     * @param id ID foto a pou efase
     * @return Repons vid ak kòd estati ki apwopriye a
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        boolean deleted = photoIService.deletePhoto(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
