package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireModuleDto {
    private String idBeneficiaire;
    private String nom;
    private String prenom;
    private String telephoneContact;
    private boolean present;  // Si benefisyè a te prezan nan modil sa a
}
