package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rencontre")
public class Rencontre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private String pointDiscussion;
    private String objectif;
    private String description;
    
    @Column(updatable = false)
    private String createBy;
    
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createDate;
    
    private String lastModifyBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date lastModifyDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processus_consultatif_id")
    @JsonBackReference("processus-rencontre")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ProcessusConsultatif processusConsultatif;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "rencontre_sections_communales",
            joinColumns = { @JoinColumn(name = "rencontre_id") },
            inverseJoinColumns = { @JoinColumn(name = "section_communale_id") })
    @JsonIgnoreProperties("rencontres")
    private Set<SectionCommunale> sectionsCommunales = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "rencontre_participants",
            joinColumns = @JoinColumn(name = "rencontre_id"),
            inverseJoinColumns = @JoinColumn(name = "personne_id"))
    @JsonIgnore
    private Set<Personne> participants = new HashSet<>();

    // Constructors
    public Rencontre() {
    }

    public Rencontre(String nom, Date date, String pointDiscussion, String objectif, String description) {
        this.nom = nom;
        this.date = date;
        this.pointDiscussion = pointDiscussion;
        this.objectif = objectif;
        this.description = description;
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

    public ProcessusConsultatif getProcessusConsultatif() {
        return processusConsultatif;
    }

    public void setProcessusConsultatif(ProcessusConsultatif processusConsultatif) {
        this.processusConsultatif = processusConsultatif;
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
    
    public Set<SectionCommunale> getSectionsCommunales() {
        return sectionsCommunales;
    }

    public void setSectionsCommunales(Set<SectionCommunale> sectionsCommunales) {
        this.sectionsCommunales = sectionsCommunales;
    }
    
    public Set<Personne> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Personne> participants) {
        this.participants = participants;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rencontre rencontre = (Rencontre) o;
        return Objects.equals(id, rencontre.id) &&
               Objects.equals(nom, rencontre.nom) &&
               Objects.equals(date, rencontre.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, date);
    }
}
