package com.natha.dev.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la requête d'ajout d'une formation à un bénéficiaire de projet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFormationToProjetBeneficiaireRequestDto {
    private String idFormation;      // ID de la formation
    private String idBeneficiaire;   // ID du bénéficiaire (peut être null)
    private String idProjet;         // ID du projet
    // Ajoutez d'autres champs nécessaires pour l'ajout d'une formation à un bénéficiaire
}
