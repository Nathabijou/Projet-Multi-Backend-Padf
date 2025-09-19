package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivrableDto {
    private Long idLivrable;
    private String nom;
    private String description;
    private LocalDate date;
    private int projetRequis;
    private int projetsRealises;
    private int projetAFaire;
    private int formationTechniqueRequis;
    private int formationTechniqueRealises;
    private int formationTechniqueAFaire;
    private int formationSocioRequis;
    private int formationSocioRealises;
    private int formationSocioAFaire;
    private Long composanteId;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    private String statut;  // Valeur pou vizibilite (pa nesesè pou pèrsistans)
}
