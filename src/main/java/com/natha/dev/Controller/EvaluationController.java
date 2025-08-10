package com.natha.dev.Controller;

import com.natha.dev.Dto.EvaluationDto;
import com.natha.dev.Dto.NoteEvaluationDto;
import com.natha.dev.Service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {
    
    private final EvaluationService evaluationService;
    
    @PutMapping("/module-beneficiaire/{moduleBeneficiaireId}")
    public ResponseEntity<EvaluationDto> mettreAJourNotes(
            @PathVariable String moduleBeneficiaireId,
            @Valid @RequestBody NoteEvaluationDto noteDto) {
        return ResponseEntity.ok(
            evaluationService.mettreAJourNotes(moduleBeneficiaireId, noteDto)
        );
    }
    
    @GetMapping("/module-beneficiaire/{moduleBeneficiaireId}")
    public ResponseEntity<EvaluationDto> getEvaluationByModuleBeneficiaire(
            @PathVariable String moduleBeneficiaireId) {
        return ResponseEntity.ok(
            evaluationService.getEvaluationByModuleBeneficiaireId(moduleBeneficiaireId)
        );
    }
}
