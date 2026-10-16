package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgressionDTO {
    private String idProgression;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double pourcentageSouhaite;
    private Double pourcentageDuJour;
    private String motif;
    private String projetId;
    private String createdBy;
    private String modifyBy;
    private Boolean active;
}
