package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleStatsDto {
    private String moduleId;
    private String moduleTitre;
    private long totalFillesInscrites;
    private long totalFillesQualifiees;
    private long totalGarconsInscrits;
    private long totalGarconsQualifies;

    // Lis chapit yo ak estatistik yo
    private List<ChapitreStatsDto> chapitres = new ArrayList<>();
}
