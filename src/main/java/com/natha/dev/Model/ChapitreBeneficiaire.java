package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chapitre_beneficiaire")
public class ChapitreBeneficiaire {

    @EmbeddedId
    private ChapitreBeneficiaireId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chapitreId")
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("beneficiaireId")
    @JoinColumn(name = "beneficiaire_id")
    private Beneficiaire beneficiaire;

    private boolean isCompleted;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    @PrePersist
    protected void onCreate() {
        if (dateDebut == null) {
            dateDebut = LocalDateTime.now();
        }
    }
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class ChapitreBeneficiaireId implements java.io.Serializable {
    private String chapitreId;
    private String beneficiaireId;
}
