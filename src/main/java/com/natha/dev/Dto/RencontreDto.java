package com.natha.dev.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

public class RencontreDto {
    private Long id;
    private String nom;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    
    private String pointDiscussion;
    private String objectif;
    private String description;
    private Long processusConsultatifId;
    
    // Champs de suivi
    private String createBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createDate;
    private String lastModifyBy;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date lastModifyDate;
    
    private List<Long> sectionCommunaleIds;

    // Constructors
    public RencontreDto() {
    }

    public RencontreDto(Long id, String nom, Date date, String pointDiscussion, String objectif, String description, Long processusConsultatifId, List<Long> sectionCommunaleIds) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.pointDiscussion = pointDiscussion;
        this.objectif = objectif;
        this.description = description;
        this.processusConsultatifId = processusConsultatifId;
        this.sectionCommunaleIds = sectionCommunaleIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPointDiscussion() {
        return pointDiscussion;
    }

    public void setPointDiscussion(String pointDiscussion) {
        this.pointDiscussion = pointDiscussion;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProcessusConsultatifId() {
        return processusConsultatifId;
    }

    public void setProcessusConsultatifId(Long processusConsultatifId) {
        this.processusConsultatifId = processusConsultatifId;
    }

    public List<Long> getSectionCommunaleIds() {
        return sectionCommunaleIds;
    }

    public void setSectionCommunaleIds(List<Long> sectionCommunaleIds) {
        this.sectionCommunaleIds = sectionCommunaleIds;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}
