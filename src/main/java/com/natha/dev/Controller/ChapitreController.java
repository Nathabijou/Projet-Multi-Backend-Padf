package com.natha.dev.Controller;

import com.natha.dev.Dto.ChapitreCreationDto;
import com.natha.dev.Dto.ChapitreDto;
import com.natha.dev.IService.IChapitreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChapitreController {

    private final IChapitreService chapitreService;

    @Autowired
    public ChapitreController(IChapitreService chapitreService) {
        this.chapitreService = chapitreService;
    }

    /**
     * Jwenn tout chapit ki nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @return Lis chapit yo nan lòd yo
     */
    @GetMapping("/api/chapitres/formation/{formationId}")
    public ResponseEntity<List<ChapitreDto>> getChapitresByFormationId(@PathVariable String formationId) {
        List<ChapitreDto> chapitres = chapitreService.getChapitresByFormationId(formationId);
        return ResponseEntity.ok(chapitres);
    }

    /**
     * Kreye yon nouvo chapit nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @param chapitreDto Done pou kreye chapit la
     * @return Chapit ki fèk kreye a
     */
    @PostMapping("/api/chapitres/formation/{formationId}")
    public ResponseEntity<ChapitreDto> createChapitre(
            @PathVariable String formationId,
            @Valid @RequestBody ChapitreCreationDto chapitreDto) {
        ChapitreDto createdChapitre = chapitreService.createChapitre(formationId, chapitreDto);
        return new ResponseEntity<>(createdChapitre, HttpStatus.CREATED);
    }

    /**
     * Kreye plizyè chapit nan yon fòmasyon
     * @param formationId ID fòmasyon an
     * @param chapitresDto Lis done pou kreye chapit yo
     * @return Lis chapit ki fèk kreye yo
     */
    @PostMapping("/api/chapitres/formation/{formationId}/batch")
    public ResponseEntity<List<ChapitreDto>> createChapitres(
            @PathVariable String formationId,
            @Valid @RequestBody List<ChapitreCreationDto> chapitresDto) {
        List<ChapitreDto> createdChapitres = chapitreService.createChapitres(formationId, chapitresDto);
        return new ResponseEntity<>(createdChapitres, HttpStatus.CREATED);
    }
}
