package com.natha.dev.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapitreCreationDto {
    @NotBlank(message = "Tit la pa dwe vid")
    private String titre;
    
    private String description;
    
    @NotNull(message = "Lòd la obligatwa")
    private Integer ordre;
    
    private String createdBy;
}
