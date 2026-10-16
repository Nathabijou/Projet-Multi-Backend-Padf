package com.natha.dev.Dao;

import com.natha.dev.Model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PresenceDao extends JpaRepository<Presence, String>{
    List<Presence> findByProjetBeneficiaireIdProjetBeneficiaire(String idProjetBeneficiaire);

    @Query("SELECT p FROM Presence p WHERE p.projetBeneficiaire.idProjetBeneficiaire = :idProjetBeneficiaire AND p.datePresence BETWEEN :debutPeriode AND :finPeriode")
    List<Presence> findByProjetBeneficiaireAndPeriode(
            @Param("idProjetBeneficiaire") String idProjetBeneficiaire,
            @Param("debutPeriode") LocalDate debutPeriode,
            @Param("finPeriode") LocalDate finPeriode
    );

}
