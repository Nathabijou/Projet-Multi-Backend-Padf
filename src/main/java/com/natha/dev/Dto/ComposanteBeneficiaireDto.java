package com.natha.dev.Dto;

import com.natha.dev.Model.ComposanteBeneficiaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComposanteBeneficiaireDto {
    private Long id;
    private Long composanteId;
    private String beneficiaireId;

    public static ComposanteBeneficiaireDto fromEntity(ComposanteBeneficiaire cb) {
        if (cb == null) {
            return null;
        }
        return new ComposanteBeneficiaireDto(
                cb.getId(),
                cb.getComposante() != null ? cb.getComposante().getId() : null,
                cb.getBeneficiaire() != null ? cb.getBeneficiaire().getIdBeneficiaire() : null
        );
    }
}
