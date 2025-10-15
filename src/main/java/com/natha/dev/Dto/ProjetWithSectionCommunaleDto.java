package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjetWithSectionCommunaleDto {
    private String idProjet;
    private String name;
    private String description;
    private LocalDate dateDebut;
    private String sectionCommunaleName;
    private Long sectionCommunaleId;
    private String quartierName;
    private Long quartierId;
}
