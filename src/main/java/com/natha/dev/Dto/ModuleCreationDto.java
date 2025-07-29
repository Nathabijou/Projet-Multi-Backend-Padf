package com.natha.dev.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuleCreationDto {
    @NotBlank(message = "Tit la pa dwe vid")
    private String titre;
    
    private String description;
    private String contenu;
    private Integer dureeEnMinutes;
    
    @NotNull(message = "Lòd la obligatwa")
    private Integer ordre;
    
    private String typeModule;
    private String urlRessource;
}
