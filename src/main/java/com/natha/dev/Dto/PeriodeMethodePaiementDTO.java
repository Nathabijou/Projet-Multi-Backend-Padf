package com.natha.dev.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeMethodePaiementDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @JsonProperty("methodePaiement")
    private String methodePaiement;
}
