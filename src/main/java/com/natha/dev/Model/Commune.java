package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import com.fasterxml.jackson.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Commune {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;


    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Groupe> groupes;

    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SectionCommunale> sectionCommunales;

    @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("commune-processus")
    @JsonIgnoreProperties({"commune", "hibernateLazyInitializer", "handler"})
    private List<ProcessusConsultatif> processusConsultatifs;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrondissement_id")
    @JsonIgnoreProperties({"communes", "hibernateLazyInitializer", "handler"})
    private Arrondissement arrondissement;

    public Arrondissement getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(Arrondissement arrondissement) {
        this.arrondissement = arrondissement;
    }

}
