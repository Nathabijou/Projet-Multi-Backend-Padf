package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moyenne")
    private Double moyenne;

    @Column(name = "note_maconnerie")
    private Double noteMaconnerie;

    @Column(name = "note_salle")
    private Double noteSalle;

    @Column(name = "note_pratique")
    private Double notePratique;

    @Column(name = "note_theorique")
    private Double noteTheorique;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @Column(length = 100)
    private String mention;

    @Column(name = "is_pass")
    private Boolean isPass;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjetBeneficiaireEvaluation> projetBeneficiaires = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_beneficiaire_id")
    private ModuleBeneficiaire moduleBeneficiaire;
    
    @PrePersist
    @PreUpdate
    public void calculerMention() {
        if (noteTheorique != null && notePratique != null) {
            this.moyenne = (noteTheorique + notePratique) / 2.0;
            this.isPass = (noteTheorique >= 60 && notePratique >= 60);
            this.mention = this.isPass ? "Admis" : "Échec";
        }
    }
}