package com.natha.dev.Controller;

import com.natha.dev.Dto.BeneficiaireEvaluationDto;
import com.natha.dev.Dto.EvaluationDto;
import com.natha.dev.Service.ProjetBeneficiaireEvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjetBeneficiaireEvaluationController {

    private final ProjetBeneficiaireEvaluationService evaluationService;

    @GetMapping("/api/projet-beneficiaire-evaluations/projet/{projetId}")
    public ResponseEntity<List<BeneficiaireEvaluationDto>> getBeneficiaireEvaluationsByProjetId(
            @PathVariable String projetId) {
        List<BeneficiaireEvaluationDto> evaluations = evaluationService.getBeneficiaireEvaluationsByProjetId(projetId);
        return ResponseEntity.ok(evaluations);
    }
    
    @GetMapping("/api/projet-beneficiaire-evaluations/projet-beneficiaire/{projetBeneficiaireId}")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsByProjetBeneficiaire(
            @PathVariable String projetBeneficiaireId) {
        List<EvaluationDto> evaluations = evaluationService.getEvaluationsByProjetBeneficiaire(projetBeneficiaireId);
        return ResponseEntity.ok(evaluations);
    }

    @PostMapping("/api/projet-beneficiaire-evaluations/projet-beneficiaire/{projetBeneficiaireId}")
    public ResponseEntity<EvaluationDto> createEvaluation(
            @PathVariable String projetBeneficiaireId,
            @Valid @RequestBody EvaluationDto evaluationDto) {
        EvaluationDto createdEvaluation = evaluationService.createEvaluation(projetBeneficiaireId, evaluationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvaluation);
    }
}
