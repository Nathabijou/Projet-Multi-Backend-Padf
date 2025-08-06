package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projet_beneficiaire_evaluation")
public class ProjetBeneficiaireEvaluation {

    @EmbeddedId
    private ProjetBeneficiaireEvaluationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projetBeneficiaireId")
    @JoinColumn(name = "projet_beneficiaire_id")
    @JsonIgnore
    private ProjetBeneficiaire projetBeneficiaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("evaluationId")
    @JoinColumn(name = "evaluation_id")
    @JsonIgnore
    private Evaluation evaluation;

    @Column(name = "note")
    private Double note;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Constructor with required fields
    public ProjetBeneficiaireEvaluation(ProjetBeneficiaire projetBeneficiaire, Evaluation evaluation) {
        this.projetBeneficiaire = projetBeneficiaire;
        this.evaluation = evaluation;
        this.id = new ProjetBeneficiaireEvaluationId(
            projetBeneficiaire.getIdProjetBeneficiaire(),
            evaluation.getId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetBeneficiaireEvaluation that = (ProjetBeneficiaireEvaluation) o;
        return Objects.equals(projetBeneficiaire, that.projetBeneficiaire) &&
               Objects.equals(evaluation, that.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetBeneficiaire, evaluation);
    }
}
