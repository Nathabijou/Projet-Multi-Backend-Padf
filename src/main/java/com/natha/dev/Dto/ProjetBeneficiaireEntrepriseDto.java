package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetBeneficiaireEntrepriseDto {
    private String idProjetBeneficiaire;
    private Long idEntreprise;
    private String nomEntreprise;
    private String descriptionEntreprise;
    private LocalDateTime dateCreation;
}
