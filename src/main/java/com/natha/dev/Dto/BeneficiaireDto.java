package com.natha.dev.Dto;

import com.natha.dev.Model.Beneficiaire;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaireDto {
    private String idBeneficiaire;
    private String nom;
    private String prenom;
    private String sexe;
    private String communeResidence;
    private String IsGraduate;
    private String filiere;
    private LocalDate dateNaissance;
    private String domaineDeFormation;
    private String typeIdentification;
    private String identification;
    private String qualification;
    private String lieuNaissance;
    private String photo;
    private String zoneFormation;
    private String departementFormation;
    private String quartierResidence;
    private String rue;
    private String numeroAdresse;
    private String localite;
    private String etatMatrimonial;
    private String relationChefMenage;
    private String milieuResidence;
    private String securiteAlimentaire;
    private String santeExemptionMedecin;
    private String santeProblemeSante;
    private Integer enfantsNesVivants;
    private Integer enfantsNesDecedes;
    private String scolariteRecente;
    private String activitePrincipale;
    private String activitePrincipaleAutre;
    private String telephoneContact;
    private String telephonePaiement;
    private String operateurPaiement;
    private String lienNaissance;
    private String typeBeneficiaire;

    public static BeneficiaireDto fromEntity(Beneficiaire b) {
        if (b == null) {
            return null;
        }
        return new BeneficiaireDto(
                b.getIdBeneficiaire(),
                b.getNom(),
                b.getPrenom(),
                b.getSexe(),
                b.getCommuneResidence(),
                b.getIsGraduate(),
                b.getFiliere(),
                b.getDateNaissance(),
                b.getDomaineDeFormation(),
                b.getTypeIdentification(),
                b.getIdentification(),
                b.getQualification(),
                b.getLieuNaissance(),
                b.getPhoto(),
                b.getZoneFormation(),
                b.getDepartementFormation(),
                b.getQuartierResidence(),
                b.getRue(),
                b.getNumeroAdresse(),
                b.getLocalite(),
                b.getEtatMatrimonial(),
                b.getRelationChefMenage(),
                b.getMilieuResidence(),
                b.getSecuriteAlimentaire(),
                b.getSanteExemptionMedecin(),
                b.getSanteProblemeSante(),
                b.getEnfantsNesVivants(),
                b.getEnfantsNesDecedes(),
                b.getScolariteRecente(),
                b.getActivitePrincipale(),
                b.getActivitePrincipaleAutre(),
                b.getTelephoneContact(),
                b.getTelephonePaiement(),
                b.getOperateurPaiement(),
                b.getLienNaissance(),
                b.getTypeBeneficiaire()

        );
    }

}
