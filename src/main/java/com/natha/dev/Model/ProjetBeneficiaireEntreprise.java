package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projet_beneficiaire_entreprise")
public class ProjetBeneficiaireEntreprise {

    @EmbeddedId
    private ProjetBeneficiaireEntrepriseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projetBeneficiaireId")
    @JoinColumn(name = "projet_beneficiaire_id")
    @JsonIgnore
    private ProjetBeneficiaire projetBeneficiaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("entrepriseId")
    @JoinColumn(name = "entreprise_id")
    @JsonIgnore
    private Entreprise entreprise;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetBeneficiaireEntreprise that = (ProjetBeneficiaireEntreprise) o;
        return Objects.equals(projetBeneficiaire, that.projetBeneficiaire) &&
               Objects.equals(entreprise, that.entreprise);
    }

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_maj")
    private LocalDateTime dateMaj;

    // Konstriktè ki pran ProjetBeneficiaire ak Entreprise kòm paramèt
    public ProjetBeneficiaireEntreprise(ProjetBeneficiaire projetBeneficiaire, Entreprise entreprise) {
        this.projetBeneficiaire = projetBeneficiaire;
        this.entreprise = entreprise;
        this.id = new ProjetBeneficiaireEntrepriseId(
            projetBeneficiaire.getIdProjetBeneficiaire(), 
            entreprise.getId()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetBeneficiaire, entreprise);
    }
}
