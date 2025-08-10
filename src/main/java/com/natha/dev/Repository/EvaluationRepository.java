package com.natha.dev.Repository;

import com.natha.dev.Model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    Optional<Evaluation> findByModuleBeneficiaireId(String moduleBeneficiaireId);
}
