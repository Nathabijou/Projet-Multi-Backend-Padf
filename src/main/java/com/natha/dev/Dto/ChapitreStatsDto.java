package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapitreStatsDto {
    private String chapitreId;
    private String titre;
    private long totalFemmes;
    private long totalHommes;
}
