package com.natha.dev.Dto;

import java.time.LocalDateTime;

public class BudgetEstimatifDetailleDTO {

    private Long id;
    private Long budgetEstimatifId;
    private String titre;
    private String description;
    private Double quantite;
    private Double coutUnitaire;
    private Double coutTotal;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime modifyDate;
    private String modifyBy;

    // Constructors
    public BudgetEstimatifDetailleDTO() {
    }

    public BudgetEstimatifDetailleDTO(Long budgetEstimatifId, String titre, String description,
                                      Double quantite, Double coutUnitaire, String createdBy) {
        this.budgetEstimatifId = budgetEstimatifId;
        this.titre = titre;
        this.description = description;
        this.quantite = quantite;
        this.coutUnitaire = coutUnitaire;
        this.coutTotal = quantite * coutUnitaire;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBudgetEstimatifId() {
        return budgetEstimatifId;
    }

    public void setBudgetEstimatifId(Long budgetEstimatifId) {
        this.budgetEstimatifId = budgetEstimatifId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getCoutUnitaire() {
        return coutUnitaire;
    }

    public void setCoutUnitaire(Double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }

    public Double getCoutTotal() {
        return coutTotal;
    }

    public void setCoutTotal(Double coutTotal) {
        this.coutTotal = coutTotal;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
