package com.natha.dev.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjetBeneficiaireEntrepriseId implements Serializable {
    
    @Column(name = "projet_beneficiaire_id")
    private String projetBeneficiaireId;
    
    @Column(name = "entreprise_id")
    private Long entrepriseId;

    // Default constructor
    public ProjetBeneficiaireEntrepriseId() {}

    public ProjetBeneficiaireEntrepriseId(String projetBeneficiaireId, Long entrepriseId) {
        this.projetBeneficiaireId = projetBeneficiaireId;
        this.entrepriseId = entrepriseId;
    }

    // Getters and Setters
    public String getProjetBeneficiaireId() {
        return projetBeneficiaireId;
    }

    public void setProjetBeneficiaireId(String projetBeneficiaireId) {
        this.projetBeneficiaireId = projetBeneficiaireId;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjetBeneficiaireEntrepriseId that = (ProjetBeneficiaireEntrepriseId) o;
        return Objects.equals(projetBeneficiaireId, that.projetBeneficiaireId) &&
               Objects.equals(entrepriseId, that.entrepriseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projetBeneficiaireId, entrepriseId);
    }
}
