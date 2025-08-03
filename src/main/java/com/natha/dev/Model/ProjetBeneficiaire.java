package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projet_beneficiaire")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetBeneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idProjetBeneficiaire;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Beneficiaire beneficiaire;

    @OneToMany(mappedBy = "projetBeneficiaire", cascade = CascadeType.ALL)
    private List<Payroll> payrolls;

    @OneToMany(mappedBy = "projetBeneficiaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ProjetBeneficiaireEntreprise> projetBeneficiaireEntreprises = new HashSet<>();


}
