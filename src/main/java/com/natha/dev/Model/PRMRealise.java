package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prm_realise")
public class PRMRealise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "quartier_id", nullable = false)
    private Quartier quartier;

    @Column(name = "beneficiaire_real")
    private Integer beneficiaireReal;

    @Column(name = "qualifier_real")
    private Integer qualifierReal;

    @Column(name = "nq_real")
    private Integer nqReal;

    @Column(name = "formation_socio_real")
    private Integer formationSocioReal;

    @Column(name = "formation_tech_real")
    private Integer formationTechReal;

    @Column(name = "date_realisation")
    private LocalDate dateRealisation;

    // Ajoute yon konstriktè san id pou fasilite kreyasyon objè
    public PRMRealise(Projet projet, Quartier quartier, Integer beneficiaireReal, Integer qualifierReal,
                      Integer nqReal, Integer formationSocioReal, Integer formationTechReal, LocalDate dateRealisation) {
        this.projet = projet;
        this.quartier = quartier;
        this.beneficiaireReal = beneficiaireReal;
        this.qualifierReal = qualifierReal;
        this.nqReal = nqReal;
        this.formationSocioReal = formationSocioReal;
        this.formationTechReal = formationTechReal;
        this.dateRealisation = dateRealisation;
    }
}
