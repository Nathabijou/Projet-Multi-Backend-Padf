package com.natha.dev.Repository;

import com.natha.dev.Model.Entreprise;
import com.natha.dev.Model.ProjetBeneficiaireEntreprise;
import com.natha.dev.Model.ProjetBeneficiaireEntrepriseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetBeneficiaireEntrepriseRepository extends JpaRepository<ProjetBeneficiaireEntreprise, ProjetBeneficiaireEntrepriseId> {
    
    @Query("SELECT pbe.entreprise FROM ProjetBeneficiaireEntreprise pbe WHERE pbe.projetBeneficiaire.idProjetBeneficiaire = :projetBeneficiaireId")
    List<Entreprise> findEntreprisesByProjetBeneficiaireId(@Param("projetBeneficiaireId") String projetBeneficiaireId);
    
    @Query("SELECT pbe FROM ProjetBeneficiaireEntreprise pbe WHERE pbe.projetBeneficiaire.projet.idProjet = :projetId")
    List<ProjetBeneficiaireEntreprise> findByProjetId(@Param("projetId") String projetId);
}
