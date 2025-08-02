package com.natha.dev.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PayrollFlatData {
    private String nomProjet;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String nom;
    private String prenom;
    private String sexe;
    private String qualification;
    private int nbJours;
    private double montantParJour;
    private String identification;
    private String telephonePaiement;
    private String methodePaiement;
    private String statut; // eg: payé, en attente, rejeté
    private String telephoneContact;

    public PayrollFlatData(String nomProjet, LocalDate dateDebut, LocalDate dateFin,
                           String nom, String prenom, String sexe, String qualification,
                           int nbJours, double montantParJour,
                           String identification, String telephonePaiement, String methodePaiement) {
        this(nomProjet, dateDebut, dateFin, nom, prenom, sexe, qualification, nbJours, 
             montantParJour, identification, telephonePaiement, methodePaiement, null, null);
    }
    
    public PayrollFlatData(String nomProjet, LocalDate dateDebut, LocalDate dateFin,
                           String nom, String prenom, String sexe, String qualification,
                           int nbJours, double montantParJour,
                           String identification, String telephonePaiement, String methodePaiement,
                           String statut) {
        this(nomProjet, dateDebut, dateFin, nom, prenom, sexe, qualification, nbJours,
             montantParJour, identification, telephonePaiement, methodePaiement, statut, null);
    }
    
    public PayrollFlatData(String nomProjet, LocalDate dateDebut, LocalDate dateFin,
                           String nom, String prenom, String sexe, String qualification,
                           int nbJours, double montantParJour,
                           String identification, String telephonePaiement, String methodePaiement,
                           String statut, String telephoneContact) {
        this.nomProjet = nomProjet;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.qualification = qualification;
        this.nbJours = nbJours;
        this.montantParJour = montantParJour;
        this.identification = identification;
        this.telephonePaiement = telephonePaiement;
        this.methodePaiement = methodePaiement;
        this.statut = statut;
        this.telephoneContact = telephoneContact;
    }



    // 🔽 Getters obligatwa pou ServiceImpl ou a mache
    public String getNomProjet() {
        return nomProjet;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public String getQualification() {
        return qualification;
    }

    public int getNbJours() {
        return nbJours;
    }

    public double getMontantParJour() {
        return montantParJour;
    }
    public String getTelephonePaiement(){
        return telephonePaiement;
    }
    public String getIdentification(){
        return identification;
    }
    public String getMethodePaiement(){
        return methodePaiement;
    }
    
    public String getStatut() {
        return statut;
    }
    
    public String getTelephoneContact() {
        return telephoneContact;
    }
}
