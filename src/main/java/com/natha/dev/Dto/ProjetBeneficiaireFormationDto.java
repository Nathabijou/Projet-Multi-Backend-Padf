package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetBeneficiaireFormationDto {
    private String id;
    private ProjetBeneficiaireDto projetBeneficiaire;
    private FormationDto formation;
}
