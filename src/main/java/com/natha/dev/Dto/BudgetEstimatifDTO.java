package com.natha.dev.Dto;

import java.time.LocalDateTime;
import java.util.List;

public class BudgetEstimatifDTO {

    private Long id;
    private String projetId;
    private String titre;
    private String description;
    private Double quantite;
    private String unite;
    private Double prixUnitaireGourdes;
    private Double totalPrixGourdes;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime modifyDate;
    private String modifyBy;
    private List<BudgetEstimatifDetailleDTO> detailles;

    // Constructors
    public BudgetEstimatifDTO() {
    }

    public BudgetEstimatifDTO(String projetId, String titre, String description, Double quantite,
                             String unite, Double prixUnitaireGourdes, String createdBy) {
        this.projetId = projetId;
        this.titre = titre;
        this.description = description;
        this.quantite = quantite;
        this.unite = unite;
        this.prixUnitaireGourdes = prixUnitaireGourdes;
        this.totalPrixGourdes = quantite * prixUnitaireGourdes;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjetId() {
        return projetId;
    }

    public void setProjetId(String projetId) {
        this.projetId = projetId;
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

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Double getPrixUnitaireGourdes() {
        return prixUnitaireGourdes;
    }

    public void setPrixUnitaireGourdes(Double prixUnitaireGourdes) {
        this.prixUnitaireGourdes = prixUnitaireGourdes;
    }

    public Double getTotalPrixGourdes() {
        return totalPrixGourdes;
    }

    public void setTotalPrixGourdes(Double totalPrixGourdes) {
        this.totalPrixGourdes = totalPrixGourdes;
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

    public List<BudgetEstimatifDetailleDTO> getDetailles() {
        return detailles;
    }

    public void setDetailles(List<BudgetEstimatifDetailleDTO> detailles) {
        this.detailles = detailles;
    }
}
