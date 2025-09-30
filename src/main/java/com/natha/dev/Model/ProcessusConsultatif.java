package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "processus_consultatif")
public class ProcessusConsultatif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private Date date;
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune_id")
    @JsonBackReference("commune-processus")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Commune commune;
    
    // Constructors
    public ProcessusConsultatif() {
        this.createDate = new Date();
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
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessusConsultatif that = (ProcessusConsultatif) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(nom, that.nom) &&
               Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, date);
    }
    
    public Commune getCommune() {
        return commune;
    }
    
    public void setCommune(Commune commune) {
        this.commune = commune;
    }
}
