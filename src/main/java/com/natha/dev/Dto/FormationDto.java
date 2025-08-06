package com.natha.dev.Dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormationDto {
    private String idFormation;
    private String titre;
    private String description;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String typeFormation;
    private String nomFormateur;
    private String createdBy;
    private String modifyBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
