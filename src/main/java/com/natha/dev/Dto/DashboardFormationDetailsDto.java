package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardFormationDetailsDto {
    // Pwojè ki gen fòmasyon
    private List<ProjetFormationDto> projetsAvecFormations;

    // Detay sou fòmasyon yo
    private List<FormationDetailDto> formations;

    // Rezime total
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjetFormationDto {
        private String idProjet;
        private String nomProjet;
        private int nombreFormations;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FormationDetailDto {
        private String idFormation;
        private String titreFormation;
        private List<ModuleDetailDto> modules;
        private int nombreBeneficiaires;
        private int nombreFemmes;
        private int nombreHommes;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuleDetailDto {
        private String idModule;
        private String titreModule;
        private List<ChapitreDetailDto> chapitres;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChapitreDetailDto {
        private String idChapitre;
        private String titreChapitre;
        private long nombreBeneficiaires;  // Chanje soti nan int pou vin long
    }
}
