package com.natha.dev.Dto;

import com.natha.dev.Model.SousEtatAvancement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtatAvancementDto {
    private String id;
    private String nom;
    private Double pourcentageTotal;
    private String description;
    private String projetId;
    private Double pourcentageRealise;
    private List<SousEtatAvancementDto> sousEtats;
}
