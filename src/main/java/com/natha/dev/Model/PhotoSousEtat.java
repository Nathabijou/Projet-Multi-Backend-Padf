package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.natha.dev.Model.SousEtatAvancement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 
 */

@Entity
@Table(name = "photo_sous_etat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoSousEtat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(nullable = false)
    private String nomFichier;
    
    @Column(name = "chemin_fichier", nullable = false)
    private String cheminFichier;
    
    @Column(name = "date_ajout", nullable = false)
    private LocalDateTime dateAjout = LocalDateTime.now();
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "est_photo_principale")
    private boolean estPhotoPrincipale = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_etat_avancement_id", nullable = false)
    @JsonIgnore
    private SousEtatAvancement sousEtatAvancement;
}
