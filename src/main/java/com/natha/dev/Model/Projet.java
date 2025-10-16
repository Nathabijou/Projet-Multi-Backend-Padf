package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"composante.projets", "hibernateLazyInitializer", "handler"})
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)") // match SQL Server
    private String idProjet;


    private String name;
    private String description;
    private String lot;
    private String address;
    private String domaineDeFormation;
    private String numeroDePatente;
    private String numeroDeReconnaissanceLegale;
    private String sourceDesNumeroDeReconnaissance;
    private String rangDePriorisation;
    private String type;
    private String statut; // planifié, en cours, terminé
    private String phase;
    private String code;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private Double latitude;
    private Double longitude;

    private Double montantMainOeuvreQualifier;
    private Double montantMainOeuvreNonQualifier;
    private Double montantAssurance;
    private Double montantMateriaux;
    private Double montantFraisCashInCashOut;
    private Double montantTotal;

    private String createdBy;
    private String modifyBy;
    private String NomRepresentant;
    private String PositionRepresentant;
    private String numeroIdentification;
    private String modeExecution;


    @Column(nullable = false)
    private Boolean active = true;

//    @PrePersist
//    private void ensureId() {
//        if (this.idProjet == null || this.idProjet.isBlank()) {
//            this.idProjet = generateRandomId(15);
//        }
//    }

    private static String generateRandomId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "composante_id")
    private Composante composante;

    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private Quartier quartier;



    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("projet-photos")
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("projet-beneficiaires")
    private List<ProjetBeneficiaire> projetBeneficiaires = new ArrayList<>();

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("projet-etat-avancement")
    private List<EtatAvancement> etatsAvancement = new ArrayList<>();

    @Transient
    private Double pourcentageAvancementTotal = 0.0;

    public void addEtatAvancement(EtatAvancement etatAvancement) {
        etatsAvancement.add(etatAvancement);
        etatAvancement.setProjet(this);
    }

    public void removeEtatAvancement(EtatAvancement etatAvancement) {
        etatsAvancement.remove(etatAvancement);
        etatAvancement.setProjet(null);
    }

    public Double calculerPourcentageAvancementTotal() {
        if (etatsAvancement == null || etatsAvancement.isEmpty()) {
            this.pourcentageAvancementTotal = 0.0;
            return 0.0;
        }
        
        double totalPondere = etatsAvancement.stream()
            .filter(Objects::nonNull)
            .mapToDouble(e -> {
                Double pourcentageTotal = e.getPourcentageTotal();
                Double pourcentageRealise = e.calculerPourcentageRealise();
                if (pourcentageTotal == null || pourcentageRealise == null) {
                    return 0.0;
                }
                return (pourcentageTotal * pourcentageRealise) / 100.0;
            })
            .sum();

        this.pourcentageAvancementTotal = Math.min(100.0, Math.max(0.0, totalPondere)); // Ensure between 0 and 100
        return this.pourcentageAvancementTotal;
    }
    
    /**
     * Get the section communale name through the quartier relationship
     * @return the name of the section communale or null if not available
     */
    public String getSectionCommunaleName() {
        return this.quartier != null && this.quartier.getSectionCommunale() != null 
               ? this.quartier.getSectionCommunale().getName() 
               : null;
    }
}
