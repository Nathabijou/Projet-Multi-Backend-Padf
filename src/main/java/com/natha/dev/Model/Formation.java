package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formation {

    @Id
    private String idFormation;

    private String titre;

    private String description;

    private LocalDate dateDebut;

    private LocalDate dateFin;
    private String typeFormation;
    private String nomFormateur;
    private String createdBy;
    private String modifyBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL)
    private List<ProjetBeneficiaireFormation> projetBeneficiaireFormations;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapitre> chapitres = new ArrayList<>();


}
