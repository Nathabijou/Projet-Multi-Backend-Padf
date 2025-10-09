package com.natha.dev.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionCommunale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "sectionCommunale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Quartier> quartiers;

    @ManyToOne
    @JsonBackReference
    private Commune commune;

    @ManyToMany(mappedBy = "sectionsCommunales", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Rencontre> rencontres = new HashSet<>();
}
