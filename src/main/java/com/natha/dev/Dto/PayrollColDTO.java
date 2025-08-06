package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayrollColDTO {
    private int nbJours;
    private double montantParJour;
    private double total;
    private String statut; // eg: payé, en attente, rejeté

    // Getters + Setters
}
