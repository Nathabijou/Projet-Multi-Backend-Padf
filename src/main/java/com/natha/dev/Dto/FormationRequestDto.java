package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationRequestDto {
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String typeFormation;
    private String nomFormateur;
}
