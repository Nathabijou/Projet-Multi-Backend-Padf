package com.natha.dev.Dao;

import com.natha.dev.Model.Formation;
import com.natha.dev.Model.ProjetBeneficiaire;
import com.natha.dev.Model.ProjetBeneficiaireFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjetBeneficiaireFormationDao extends JpaRepository<ProjetBeneficiaireFormation, String> {

    boolean existsByProjetBeneficiaireAndFormation(ProjetBeneficiaire projetBeneficiaire, Formation formation);

    Optional<Object> findByProjetBeneficiaireAndFormationIdFormation(ProjetBeneficiaire pb, String idFormation);
    
    List<ProjetBeneficiaireFormation> findByFormation(Formation formation);
    
    Optional<ProjetBeneficiaireFormation> findByProjetBeneficiaireAndFormation(ProjetBeneficiaire projetBeneficiaire, Formation formation);
    
    @Query("SELECT DISTINCT p.formation FROM ProjetBeneficiaireFormation p WHERE p.projetBeneficiaire.projet.idProjet = :projetId")
    List<Formation> findFormationsByProjetId(@Param("projetId") String projetId);
}
