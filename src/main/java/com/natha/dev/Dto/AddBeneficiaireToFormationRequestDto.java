package com.natha.dev.Dto;

import lombok.Data;

@Data
public class AddBeneficiaireToFormationRequestDto {
    private String idProjet;
    private String idBeneficiaire;
    private String idFormation;
}
