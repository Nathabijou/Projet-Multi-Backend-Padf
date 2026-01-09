package com.natha.dev.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stock_items")
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(name = "quantite_entree", nullable = false)
    private Double quantiteEntree = 0.0;

    @Column(name = "quantite_sortie", nullable = false)
    private Double quantiteSortie = 0.0;

    @Column(name = "quantite_actuelle", nullable = false)
    private Double quantiteActuelle = 0.0;

    @Column(name = "seuil_minimum")
    private Double seuilMinimum = 0.0;

    @Column(name = "valeur_totale")
    private Double valeurTotale = 0.0;

    @Column(name = "prix_moyen")
    private Double prixMoyen = 0.0;

    @Column(name = "derniere_mise_a_jour")
    private LocalDateTime derniereMiseAJour;

    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @Column(name = "etat")
    private String etat; // DISPONIBLE, RUPTURE, EN_REAPPROVISIONNEMENT

    @OneToMany(mappedBy = "stockItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MouvementStock> mouvements;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Constructors
    public StockItem() {
    }

    public StockItem(Stock stock, Article article, Double quantiteEntree, Double quantiteSortie,
                     Double seuilMinimum, String etat) {
        this.stock = stock;
        this.article = article;
        this.quantiteEntree = quantiteEntree;
        this.quantiteSortie = quantiteSortie;
        this.quantiteActuelle = quantiteEntree - quantiteSortie;
        this.seuilMinimum = seuilMinimum;
        this.etat = etat;
        this.dateCreation = LocalDateTime.now();
        this.derniereMiseAJour = LocalDateTime.now();
        this.prixMoyen = article.getPrixMoyen();
        this.valeurTotale = this.quantiteActuelle * this.prixMoyen;
    }

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        derniereMiseAJour = LocalDateTime.now();
        if (quantiteActuelle == null) {
            quantiteActuelle = quantiteEntree - quantiteSortie;
        }
        if (valeurTotale == null) {
            valeurTotale = quantiteActuelle * prixMoyen;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        derniereMiseAJour = LocalDateTime.now();
        quantiteActuelle = quantiteEntree - quantiteSortie;
        valeurTotale = quantiteActuelle * prixMoyen;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Double getQuantiteEntree() {
        return quantiteEntree;
    }

    public void setQuantiteEntree(Double quantiteEntree) {
        this.quantiteEntree = quantiteEntree;
    }

    public Double getQuantiteSortie() {
        return quantiteSortie;
    }

    public void setQuantiteSortie(Double quantiteSortie) {
        this.quantiteSortie = quantiteSortie;
    }

    public Double getQuantiteActuelle() {
        return quantiteActuelle;
    }

    public void setQuantiteActuelle(Double quantiteActuelle) {
        this.quantiteActuelle = quantiteActuelle;
    }

    public Double getSeuilMinimum() {
        return seuilMinimum;
    }

    public void setSeuilMinimum(Double seuilMinimum) {
        this.seuilMinimum = seuilMinimum;
    }

    public Double getValeurTotale() {
        return valeurTotale;
    }

    public void setValeurTotale(Double valeurTotale) {
        this.valeurTotale = valeurTotale;
    }

    public Double getPrixMoyen() {
        return prixMoyen;
    }

    public void setPrixMoyen(Double prixMoyen) {
        this.prixMoyen = prixMoyen;
    }

    public LocalDateTime getDerniereMiseAJour() {
        return derniereMiseAJour;
    }

    public void setDerniereMiseAJour(LocalDateTime derniereMiseAJour) {
        this.derniereMiseAJour = derniereMiseAJour;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<MouvementStock> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<MouvementStock> mouvements) {
        this.mouvements = mouvements;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
