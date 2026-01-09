package com.natha.dev.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "budget_estimatif")
public class BudgetEstimatif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double quantite;

    @Column(nullable = false)
    private String unite;

    @Column(name = "prix_unitaire_gourdes", nullable = false)
    private Double prixUnitaireGourdes;

    @Column(name = "total_prix_gourdes", nullable = false)
    private Double totalPrixGourdes;

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

    @OneToMany(mappedBy = "budgetEstimatif", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BudgetEstimatifDetaille> detailles = new ArrayList<>();

    // Constructors
    public BudgetEstimatif() {
    }

    public BudgetEstimatif(Projet projet, String titre, String description, Double quantite,
                          String unite, Double prixUnitaireGourdes, String createdBy, String tenantId) {
        this.projet = projet;
        this.titre = titre;
        this.description = description;
        this.quantite = quantite;
        this.unite = unite;
        this.prixUnitaireGourdes = prixUnitaireGourdes;
        this.totalPrixGourdes = quantite * prixUnitaireGourdes;
        this.createdBy = createdBy;
        this.tenantId = tenantId;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        modifyDate = LocalDateTime.now();
        if (totalPrixGourdes == null) {
            totalPrixGourdes = quantite * prixUnitaireGourdes;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        modifyDate = LocalDateTime.now();
        totalPrixGourdes = quantite * prixUnitaireGourdes;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public List<BudgetEstimatifDetaille> getDetailles() {
        return detailles;
    }

    public void setDetailles(List<BudgetEstimatifDetaille> detailles) {
        this.detailles = detailles;
    }
}
