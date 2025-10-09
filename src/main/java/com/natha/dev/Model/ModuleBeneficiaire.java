package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"module_id", "beneficiaire_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleBeneficiaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Beneficiaire beneficiaire;
    
    private boolean isCompleted = false;
    private LocalDateTime dateInscription = LocalDateTime.now();
    private LocalDateTime dateCompletion;
    
    // Additional fields can be added as needed, like:
    // - progress percentage
    // - last accessed date
    // - etc.
    
    @OneToOne(mappedBy = "moduleBeneficiaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Evaluation evaluation;

    
    @PrePersist
    protected void onCreate() {
        this.dateInscription = LocalDateTime.now();
    }
}
