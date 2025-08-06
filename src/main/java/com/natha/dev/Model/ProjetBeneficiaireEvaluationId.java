package com.natha.dev.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjetBeneficiaireEvaluationId implements Serializable {
    
    @Column(name = "projet_beneficiaire_id")
    private String projetBeneficiaireId;
    
    @Column(name = "evaluation_id")
    private Long evaluationId;

    // Default constructor
    public ProjetBeneficiaireEvaluationId() {}

    public ProjetBeneficiaireEvaluationId(String projetBeneficiaireId, Long evaluationId) {
        this.projetBeneficiaireId = projetBeneficiaireId;
        this.evaluationId = evaluationId;
    }

    // Getters and Setters
    public String getProjetBeneficiaireId() {
        return projetBeneficiaireId;
    }

    public void setProjetBeneficiaireId(String projetBeneficiaireId) {
        this.projetBeneficiaireId = projetBeneficiaireId;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetBeneficiaireEvaluationId that = (ProjetBeneficiaireEvaluationId) o;
        return Objects.equals(projetBeneficiaireId, that.projetBeneficiaireId) &&
               Objects.equals(evaluationId, that.evaluationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetBeneficiaireId, evaluationId);
    }
}
