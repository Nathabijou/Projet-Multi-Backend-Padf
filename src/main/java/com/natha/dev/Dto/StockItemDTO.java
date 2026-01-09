package com.natha.dev.Dto;

import java.time.LocalDateTime;
import java.util.List;

public class StockItemDTO {

    private Long id;
    private Long stockId;
    private Long articleId;
    private String articleCode;
    private String articleNom;
    private Double quantiteEntree;
    private Double quantiteSortie;
    private Double quantiteActuelle;
    private Double seuilMinimum;
    private Double valeurTotale;
    private Double prixMoyen;
    private LocalDateTime derniereMiseAJour;
    private LocalDateTime dateCreation;
    private String etat;
    private List<MouvementStockDTO> mouvements;
    private String commentaire;

    // Constructors
    public StockItemDTO() {
    }

    public StockItemDTO(Long stockId, Long articleId, Double quantiteEntree, Double quantiteSortie,
                        Double seuilMinimum, String etat) {
        this.stockId = stockId;
        this.articleId = articleId;
        this.quantiteEntree = quantiteEntree;
        this.quantiteSortie = quantiteSortie;
        this.quantiteActuelle = quantiteEntree - quantiteSortie;
        this.seuilMinimum = seuilMinimum;
        this.etat = etat;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleNom() {
        return articleNom;
    }

    public void setArticleNom(String articleNom) {
        this.articleNom = articleNom;
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

    public List<MouvementStockDTO> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<MouvementStockDTO> mouvements) {
        this.mouvements = mouvements;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
