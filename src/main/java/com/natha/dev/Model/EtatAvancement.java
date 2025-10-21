package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "etat_avancement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EtatAvancement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(name = "pourcentage_total")
    private double pourcentageTotal;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", referencedColumnName = "idProjet", nullable = false)
    @JsonIgnore
    private Projet projet;
    
    @OneToMany(mappedBy = "etatAvancement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SousEtatAvancement> sousEtats;
    
    @Transient
    private double pourcentageRealise;
    
    public double calculerPourcentageRealise() {
        if (sousEtats == null || sousEtats.isEmpty()) {
            return 0.0;
        }
        
        double totalPoids = sousEtats.stream()
            .filter(se -> se.getPoids() != null)
            .mapToDouble(SousEtatAvancement::getPoids)
            .sum();
            
        if (totalPoids == 0) {
            return 0.0;
        }
        
        double totalRealise = sousEtats.stream()
            .filter(se -> se.getPoids() != null && se.getPourcentageRealise() != null)
            .mapToDouble(se -> (se.getPoids() / totalPoids) * se.getPourcentageRealise())
            .sum();
            
        this.pourcentageRealise = totalRealise;
        return totalRealise;
    }
    
    // Ajout de la méthode setProjet manquante
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    // Ajout de la méthode getPourcentageTotal manquante
    public Double getPourcentageTotal() {
        return this.pourcentageTotal;
    }
}
