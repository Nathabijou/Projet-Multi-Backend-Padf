package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    private String idModule;
    private String titre;
    private String description;
    private String contenu;
    private Integer dureeEnMinutes;
    private Integer ordre;
    private String typeModule;
    private String urlRessource;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String chapitreId;  // Sèlman ID chapit la pou evite sikilè referans
}
