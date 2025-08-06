package com.natha.dev.Service;

import com.natha.dev.Dto.BeneficiaireEvaluationDto;
import com.natha.dev.Dto.EvaluationDto;
import com.natha.dev.Model.ProjetBeneficiaire;

import java.util.List;

public interface ProjetBeneficiaireEvaluationService {
    List<BeneficiaireEvaluationDto> getBeneficiaireEvaluationsByProjetId(String projetId);
    List<EvaluationDto> getEvaluationsByProjetBeneficiaire(String projetBeneficiaireId);
    
    /**
     * Kreye yon nouvo evalyasyon pou yon ProjetBeneficiaire
     * @param projetBeneficiaireId ID ProjetBeneficiaire a
     * @param evaluationDto Done evalyasyon an
     * @return EvaluationDto ki fèk kreye a
     */
    EvaluationDto createEvaluation(String projetBeneficiaireId, EvaluationDto evaluationDto);
}
