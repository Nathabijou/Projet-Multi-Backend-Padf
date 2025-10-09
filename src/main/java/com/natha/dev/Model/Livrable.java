package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "livrables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livrable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livrable")
    private Long idLivrable;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    @Column(name = "projet_requis")
    private int projetRequis;

    @Column(name = "projets_realises")
    private int projetsRealises;

    @Column(name = "projet_a_faire")
    private int projetAFaire;

    @Column(name = "formation_technique_requis")
    private int formationTechniqueRequis;

    @Column(name = "formation_technique_realises")
    private int formationTechniqueRealises;

    @Column(name = "formation_technique_a_faire")
    private int formationTechniqueAFaire;

    @Column(name = "formation_socio_requis")
    private int formationSocioRequis;

    @Column(name = "formation_socio_realises")
    private int formationSocioRealises;

    @Column(name = "formation_socio_a_faire")
    private int formationSocioAFaire;

    @ManyToOne
    @JoinColumn(name = "composante_id", nullable = false)
    private Composante composante;

    private String createdBy;
    private String updatedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatutLivrable statut = StatutLivrable.EN_COURS;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
