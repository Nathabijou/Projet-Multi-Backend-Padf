package com.natha.dev.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "unite")
    private String unite;

    @Column(name = "poids_volume")
    private Double poidsVolume;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "prix_moyen")
    private Double prixMoyen;

    @Column(name = "devise")
    private String devise;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "actif", nullable = false)
    private Boolean actif = true;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "created_by_id")
    private Long createdBy;

    @Column(name = "updated_by_id")
    private Long updatedBy;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    // Constructors
    public Article() {
    }

    public Article(String code, String nom, String marque, String modele, String description,
                   String categorie, String unite, Double poidsVolume, Double prixUnitaire,
                   Double prixMoyen, String devise, String imageUrl, Boolean actif,
                   Long createdBy, Long updatedBy) {
        this.code = code;
        this.nom = nom;
        this.marque = marque;
        this.modele = modele;
        this.description = description;
        this.categorie = categorie;
        this.unite = unite;
        this.poidsVolume = poidsVolume;
        this.prixUnitaire = prixUnitaire;
        this.prixMoyen = prixMoyen;
        this.devise = devise;
        this.imageUrl = imageUrl;
        this.actif = actif;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Double getPoidsVolume() {
        return poidsVolume;
    }

    public void setPoidsVolume(Double poidsVolume) {
        this.poidsVolume = poidsVolume;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getPrixMoyen() {
        return prixMoyen;
    }

    public void setPrixMoyen(Double prixMoyen) {
        this.prixMoyen = prixMoyen;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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
}
