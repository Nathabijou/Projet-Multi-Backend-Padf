package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleBeneficiaireDto {
    private String id;
    private String moduleId;
    private String moduleTitre;
    private String beneficiaireId;
    private String beneficiaireNom;
    private String beneficiairePrenom;
    private boolean isCompleted;
    private LocalDateTime dateInscription;
    private LocalDateTime dateCompletion;
}
