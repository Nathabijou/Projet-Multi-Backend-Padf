package com.natha.dev.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_estimatif_detaille")
public class BudgetEstimatifDetaille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "budget_estimatif_id", nullable = false)
    private BudgetEstimatif budgetEstimatif;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double quantite;

    @Column(name = "cout_unitaire", nullable = false)
    private Double coutUnitaire;

    @Column(name = "cout_total", nullable = false)
    private Double coutTotal;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "modify_by")
    private String modifyBy;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Constructors
    public BudgetEstimatifDetaille() {
    }

    public BudgetEstimatifDetaille(BudgetEstimatif budgetEstimatif, String titre, String description,
                                   Double quantite, Double coutUnitaire, String createdBy, String tenantId) {
        this.budgetEstimatif = budgetEstimatif;
        this.titre = titre;
        this.description = description;
        this.quantite = quantite;
        this.coutUnitaire = coutUnitaire;
        this.coutTotal = quantite * coutUnitaire;
        this.createdBy = createdBy;
        this.tenantId = tenantId;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        modifyDate = LocalDateTime.now();
        if (coutTotal == null) {
            coutTotal = quantite * coutUnitaire;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifyDate = LocalDateTime.now();
        coutTotal = quantite * coutUnitaire;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BudgetEstimatif getBudgetEstimatif() {
        return budgetEstimatif;
    }

    public void setBudgetEstimatif(BudgetEstimatif budgetEstimatif) {
        this.budgetEstimatif = budgetEstimatif;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
