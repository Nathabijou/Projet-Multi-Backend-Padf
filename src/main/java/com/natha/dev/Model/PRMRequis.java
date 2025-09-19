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
@Table(name = "prm_requis")
public class PRMRequis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "quartier_id", nullable = false)
    private Quartier quartier;

    @Column(name = "beneficiaire_plan")
    private Integer beneficiairePlan;

    @Column(name = "qualifier_plan")
    private Integer qualifierPlan;

    @Column(name = "nq_plan")
    private Integer nqPlan;

    @Column(name = "formation_socio_plan")
    private Integer formationSocioPlan;

    @Column(name = "formation_tech_plan")
    private Integer formationTechPlan;

    @Column(name = "date_planification")
    private LocalDate datePlanification;

    // Ajoute yon konstriktè san id pou fasilite kreyasyon objè
    public PRMRequis(Projet projet, Quartier quartier, Integer beneficiairePlan, Integer qualifierPlan,
                     Integer nqPlan, Integer formationSocioPlan, Integer formationTechPlan, LocalDate datePlanification) {
        this.projet = projet;
        this.quartier = quartier;
        this.beneficiairePlan = beneficiairePlan;
        this.qualifierPlan = qualifierPlan;
        this.nqPlan = nqPlan;
        this.formationSocioPlan = formationSocioPlan;
        this.formationTechPlan = formationTechPlan;
        this.datePlanification = datePlanification;
    }
}
