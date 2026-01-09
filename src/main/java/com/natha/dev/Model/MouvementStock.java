package com.natha.dev.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mouvements_stock")
public class MouvementStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

    @Column(name = "type_mouvement", nullable = false)
    private String typeMouvement; // ENTREE, SORTIE, TRANSFERT, AJUSTEMENT

    @Column(name = "quantite", nullable = false)
    private Double quantite;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement;

    @Column(name = "created_by_id")
    private Long createdBy;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Constructors
    public MouvementStock() {
    }

    public MouvementStock(StockItem stockItem, String typeMouvement, Double quantite,
                          Double prixUnitaire, String description, Long createdBy) {
        this.stockItem = stockItem;
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

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
