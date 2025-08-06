package com.natha.dev.Repository;

import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetBeneficiaireEvaluationRepository extends JpaRepository<ProjetBeneficiaireEvaluation, Long> {
    
    List<ProjetBeneficiaireEvaluation> findByProjetBeneficiaire(ProjetBeneficiaire projetBeneficiaire);
    
    boolean existsByProjetBeneficiaireAndEvaluationId(ProjetBeneficiaire projetBeneficiaire, Long evaluationId);
}
