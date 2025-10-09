package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationDashboardDto {
    private long totalProjetsAvecFormation;
    private long totalFormations;
    private long totalModules;
    private long totalChapitres;
    private long totalBeneficiairesFormation;
    private long totalFemmesFormation;
    private long totalHommesFormation;
    private long totalFemmesQualifieesFormation;
    private long totalFemmesNonQualifieesFormation;
    private long totalHommesQualifiesFormation;
    private long totalHommesNonQualifiesFormation;

    // Ajoutez d'autres champs selon vos besoins
}
