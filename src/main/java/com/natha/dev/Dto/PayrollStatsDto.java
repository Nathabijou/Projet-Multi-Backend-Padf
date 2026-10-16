package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayrollStatsDto {
    private String locationType; // COMPOSANTE, ZONE, DEPARTEMENT, ARRONDISSEMENT, COMMUNE, SECTION_COMMUNALE, QUARTIER, PROJET
    private Long locationId;
    private String locationName;
    private Long nombrePayroll;
    private Long nombreBeneficiaire;
    private Long hommeMois; // nombreBeneficiaire * nombrePayroll
}
