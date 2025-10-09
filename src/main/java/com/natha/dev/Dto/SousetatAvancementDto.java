package com.natha.dev.Dto;

import java.time.LocalDateTime;
import java.util.List;

import com.natha.dev.Dto.PhotoSousEtatDto;

public class SousEtatAvancementDto {
    private String id;
    private String libelle;
    private String description;
    private String etatAvancementId; // Reference to parent EtatAvancement
    private LocalDateTime dateCreation;
    private String createdBy;
    private LocalDateTime dateModification;
    private String modifiedBy;
    private Double pourcentageRealise;
    private Double poids;
    private List<PhotoSousEtatDto> photos;

    // Getters and Setters
    public List<PhotoSousEtatDto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoSousEtatDto> photos) {
        this.photos = photos;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatAvancementId() {
        return etatAvancementId;
    }

    public void setEtatAvancementId(String etatAvancementId) {
        this.etatAvancementId = etatAvancementId;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Double getPourcentageRealise() {
        return pourcentageRealise;
    }

    public void setPourcentageRealise(Double pourcentageRealise) {
        this.pourcentageRealise = pourcentageRealise;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }
}
