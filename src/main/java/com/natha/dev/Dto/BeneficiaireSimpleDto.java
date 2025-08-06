package com.natha.dev.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaireSimpleDto {
    private String idBeneficiaire;
    private String nom;
    private String prenom;
    private String sexe;
    private String communeResidence;
    private String filiere;
    private LocalDate dateNaissance;
    private String domaineDeFormation;
    private String typeIdentification;
    private String identification;
    private String qualification;
    private String telephoneContact;
    private String telephonePaiement;
    private String operateurPaiement;
    private String lienNaissance;
    private String typeBeneficiaire;
}
