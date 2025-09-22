package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSousEtatDto {
    private String id;
    private String nomFichier;
    private String cheminFichier;
    private LocalDateTime dateAjout;
    private String description;
    private boolean estPhotoPrincipale;
}
