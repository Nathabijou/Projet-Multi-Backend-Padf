package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireEvaluationDto {
    private String id;
    private Long evaluationId;  // ID evalyasyon an nan tab Evaluation
    private String nom;
    private String prenom;
    private String sexe;
    private String communeResidence;
    private Double noteMaconnerie;
    private Double noteSalle;
    private Double moyenne;
    private String mention;
    private Boolean isPass;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String commentaire;
    private String note;
}
