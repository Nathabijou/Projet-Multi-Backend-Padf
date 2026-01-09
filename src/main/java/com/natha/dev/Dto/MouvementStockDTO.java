package com.natha.dev.Dto;

import java.time.LocalDateTime;

public class MouvementStockDTO {

    private Long id;
    private Long stockItemId;
    private String typeMouvement;
    private Double quantite;
    private Double prixUnitaire;
    private String description;
    private LocalDateTime dateMouvement;
    private Long createdBy;

    // Constructors
    public MouvementStockDTO() {
    }

    public MouvementStockDTO(Long stockItemId, String typeMouvement, Double quantite,
                             Double prixUnitaire, String description, Long createdBy) {
        this.stockItemId = stockItemId;
        this.typeMouvement = typeMouvement;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.description = description;
        this.createdBy = createdBy;
        this.dateMouvement = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Long stockItemId) {
        this.stockItemId = stockItemId;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
