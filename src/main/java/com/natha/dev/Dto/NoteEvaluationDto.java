package com.natha.dev.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteEvaluationDto {
    @NotNull(message = "Nòt teyorik la obligatwa")
    @Min(value = 0, message = "Nòt teyorik la dwe ant 0 ak 100")
    @Max(value = 100, message = "Nòt teyorik la dwe ant 0 ak 100")
    private Double noteTheorique;
    
    @NotNull(message = "Nòt pratik la obligatwa")
    @Min(value = 0, message = "Nòt pratik la dwe ant 0 ak 100")
    @Max(value = 100, message = "Nòt pratik la dwe ant 0 ak 100")
    private Double notePratique;
    
    private String commentaire;
}
