package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "sous_etat_avancement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SousEtatAvancement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(name = "pourcentage_realise")
    private Double pourcentageRealise;
    
    @Column(name = "poids")
    private Double poids;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etat_avancement_id", nullable = false)
    @JsonIgnore
    private EtatAvancement etatAvancement;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_etat_avancement_id")
    private List<PhotoSousEtat> photos;
    
    @Transient
    private String etatAvancementId;
    
    @PostLoad
    private void setTransientFields() {
        if (etatAvancement != null) {
            this.etatAvancementId = etatAvancement.getId();
        }
    }
}
