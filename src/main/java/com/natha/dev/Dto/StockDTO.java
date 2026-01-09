package com.natha.dev.Dto;

import java.time.LocalDateTime;

public class StockDTO {

    private Long id;
    private String name;
    private String description;
    private String type;
    private Long composanteId;
    private String projetId;
    private String address;
    private Long managerId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String status;
    private String gestionType;
    private String evaluationMethod;
    private Double taxRate;
    private String currency;
    private Boolean shareable;
    private Boolean temporary;
    private Long createdBy;
    private Long updatedBy;
    private Double quantite;
    private String uniteMesure;
    private Double prixUnitaireUsd;
    private Double prixUnitaireHtg;
    private String titre;

    // Constructors
    public StockDTO() {
    }

    public StockDTO(String name, String description, String type, Long composanteId,
                    String projetId, String address, Long managerId, String status, String gestionType,
                    String evaluationMethod, Double taxRate, String currency,
                    Boolean shareable, Boolean temporary, Long createdBy, Long updatedBy) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.composanteId = composanteId;
        this.projetId = projetId;
        this.address = address;
        this.managerId = managerId;
        this.status = status;
        this.gestionType = gestionType;
        this.evaluationMethod = evaluationMethod;
        this.taxRate = taxRate;
        this.currency = currency;
        this.shareable = shareable;
        this.temporary = temporary;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getComposanteId() {
        return composanteId;
    }

    public void setComposanteId(Long composanteId) {
        this.composanteId = composanteId;
    }

    public String getProjetId() {
        return projetId;
    }

    public void setProjetId(String projetId) {
        this.projetId = projetId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGestionType() {
        return gestionType;
    }

    public void setGestionType(String gestionType) {
        this.gestionType = gestionType;
    }

    public String getEvaluationMethod() {
        return evaluationMethod;
    }

    public void setEvaluationMethod(String evaluationMethod) {
        this.evaluationMethod = evaluationMethod;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getShareable() {
        return shareable;
    }

    public void setShareable(Boolean shareable) {
        this.shareable = shareable;
    }

    public Boolean getTemporary() {
        return temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public String getUniteMesure() {
        return uniteMesure;
    }

    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }

    public Double getPrixUnitaireUsd() {
        return prixUnitaireUsd;
    }

    public void setPrixUnitaireUsd(Double prixUnitaireUsd) {
        this.prixUnitaireUsd = prixUnitaireUsd;
    }

    public Double getPrixUnitaireHtg() {
        return prixUnitaireHtg;
    }

    public void setPrixUnitaireHtg(Double prixUnitaireHtg) {
        this.prixUnitaireHtg = prixUnitaireHtg;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
