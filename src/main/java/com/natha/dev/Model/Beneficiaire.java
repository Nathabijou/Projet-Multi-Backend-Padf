package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiaire {

    @Id
    @Column(length = 15, columnDefinition = "varchar(15)") // match JoinColumn
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


    @PrePersist
    public void generateId() {
        if (this.idBeneficiaire == null) {
            this.idBeneficiaire = generateCustomId(15);
        }
    }

    private String generateCustomId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }


    @OneToMany(mappedBy = "beneficiaire")
    private List<ProjetBeneficiaire> projetBeneficiaires;

}
