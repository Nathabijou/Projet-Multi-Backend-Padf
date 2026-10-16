package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, columnDefinition = "varchar(36)")
    private String idProgression = "";

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double pourcentageSouhaite;
    private Double pourcentageDuJour;
    private String motif = "";

    private String createdBy = "";
    private String modifyBy = "";

    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    @JsonIgnore
    private Projet projet;

}
