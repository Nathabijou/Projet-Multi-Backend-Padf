package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idChapitre;

    @Column(nullable = false)
    private String titre;

    private String description;
    private Integer ordre;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    @OneToMany(mappedBy = "chapitre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
