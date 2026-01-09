package com.natha.dev.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockType type;

    @Column(name = "composante_id")
    private Long composanteId;

    @Column(name = "projet_id", columnDefinition = "VARCHAR(255)")
    private String projetId;

    @Column(nullable = false)
    private String address;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime updateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GestionType gestionType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EvaluationMethod evaluationMethod;

    @Column(nullable = false)
    private Double taxRate;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Boolean shareable;

    @Column(nullable = false)
    private Boolean temporary;

    @Column(name = "created_by_id")
    private Long createdBy;

    @Column(name = "updated_by_id")
    private Long updatedBy;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "quantite")
    private Double quantite = 0.0;

    @Column(name = "unite_mesure")
    private String uniteMesure;

    @Column(name = "prix_unitaire_usd")
    private Double prixUnitaireUsd;

    @Column(name = "prix_unitaire_htg")
    private Double prixUnitaireHtg;

    @Column(name = "titre")
    private String titre;

    // Constructors
    public Stock() {
    }

    public Stock(String name, String description, StockType type, String address,
                 StockStatus status, GestionType gestionType, EvaluationMethod evaluationMethod,
                 Double taxRate, String currency, Boolean shareable, Boolean temporary, String tenantId) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.address = address;
        this.status = status;
        this.gestionType = gestionType;
        this.evaluationMethod = evaluationMethod;
        this.taxRate = taxRate;
        this.currency = currency;
        this.shareable = shareable;
        this.temporary = temporary;
        this.tenantId = tenantId;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
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

    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
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

    public StockStatus getStatus() {
        return status;
    }

    public void setStatus(StockStatus status) {
        this.status = status;
    }

    public GestionType getGestionType() {
        return gestionType;
    }

    public void setGestionType(GestionType gestionType) {
        this.gestionType = gestionType;
    }

    public EvaluationMethod getEvaluationMethod() {
        return evaluationMethod;
    }

    public void setEvaluationMethod(EvaluationMethod evaluationMethod) {
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    // Enums
    public enum StockType {
        PRINCIPAL, PROJET, TEMPORAIRE, DISTRIBUTION
    }

    public enum StockStatus {
        ACTIF, ARCHIVÉ, BLOQUÉ
    }

    public enum GestionType {
        MANUEL, AUTO
    }

    public enum EvaluationMethod {
        FIFO, LIFO, MOYEN
    }
}
