package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Nouvo relasyon @OneToMany ki pwen sou tab jonksyon an
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneDepartement> zoneDepartements;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    private List<Arrondissement> arrondissements;

}
