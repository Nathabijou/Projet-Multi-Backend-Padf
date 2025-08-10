package com.natha.dev.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDto {
    private Long id;
    private Long evaluationId;  // Pou estoke ID evalyasyon an
    private Double noteMaconnerie;
    private Double noteSalle;
    private Double notePratique;
    private Double noteTheorique;
    @NotNull(message = "Mwayèn an obligatwa")
    @Min(value = 0, message = "Mwayèn an pa ka pi piti pase 0")
    @Max(value = 100, message = "Mwayèn an pa ka pi gran pase 100")
    private Double moyenne;
    private String mention;
    private Boolean isPass;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String commentaire;
    private Double note; // Note specifik pou benefisyè a nan evalyasyon sa a
}
