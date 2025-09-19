package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PRMAfaireDTO {
    private String projetId;
    private Long quartierId;
    private Integer beneficiaireAFaire;
    private Integer qualifierAFaire;
    private Integer nqAFaire;
    private Integer formationSocioAFaire;
    private Integer formationTechAFaire;

    // Konstriktè san paramèt deja genyen akòz @NoArgsConstructor

    // Konstriktè ak tout paramèt deja genyen akòz @AllArgsConstructor

    // Metòd fabrik pou fasilite kreyasyon objè
    public static PRMAfaireDTO of(String projetId, Long quartierId, Integer beneficiaireAFaire,
                                  Integer qualifierAFaire, Integer nqAFaire,
                                  Integer formationSocioAFaire, Integer formationTechAFaire) {
        PRMAfaireDTO dto = new PRMAfaireDTO();
        dto.setProjetId(projetId);
        dto.setQuartierId(quartierId);
        dto.setBeneficiaireAFaire(beneficiaireAFaire);
        dto.setQualifierAFaire(qualifierAFaire);
        dto.setNqAFaire(nqAFaire);
        dto.setFormationSocioAFaire(formationSocioAFaire);
        dto.setFormationTechAFaire(formationTechAFaire);
        return dto;
    }
}
