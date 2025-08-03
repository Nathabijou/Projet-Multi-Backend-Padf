package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "entreprises")
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    private  String adresse;

    @Column(name = "create_by")
    private String createBy;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProjetBeneficiaireEntreprise> projetBeneficiaireEntreprises = new HashSet<>();
}
