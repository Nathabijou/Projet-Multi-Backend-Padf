package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "personne")
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String prenom;
    private String sexe;
    private String adresse;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private String email;
    private String telephone;
    private String institution;
    private String typePersonne;
    private String identification;
    
    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private Set<Rencontre> rencontres = new HashSet<>();
    
    // Constructors
    public Personne() {
    }
    
    public Personne(String nom, String prenom, String sexe, String adresse, Date dateNaissance, 
                   String email, String telephone, String institution, String typePersonne, String identification) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.institution = institution;
        this.typePersonne = typePersonne;
        this.identification = identification;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    
    public String getTypePersonne() { return typePersonne; }
    public void setTypePersonne(String typePersonne) { this.typePersonne = typePersonne; }
    
    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }
    
    public Set<Rencontre> getRencontres() { return rencontres; }
    public void setRencontres(Set<Rencontre> rencontres) { this.rencontres = rencontres; }
    
    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return Objects.equals(id, personne.id) &&
               Objects.equals(identification, personne.identification);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identification);
    }
}
