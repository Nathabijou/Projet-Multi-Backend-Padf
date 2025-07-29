package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapitreDto {
    private String idChapitre;
    private String titre;
    private String description;
    private Integer ordre;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String formationId;  // Sèlman ID fòmasyon an pou evite sikilè referans
}
