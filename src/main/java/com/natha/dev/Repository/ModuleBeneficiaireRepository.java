package com.natha.dev.Repository;

import com.natha.dev.Model.Beneficiaire;
import com.natha.dev.Model.Module;
import com.natha.dev.Model.ModuleBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModuleBeneficiaireRepository extends JpaRepository<ModuleBeneficiaire, String> {
    
    boolean existsByModuleAndBeneficiaire(Module module, Beneficiaire beneficiaire);
    
    Optional<ModuleBeneficiaire> findByModuleAndBeneficiaire(Module module, Beneficiaire beneficiaire);
    
    List<ModuleBeneficiaire> findByModule(Module module);
    
    List<ModuleBeneficiaire> findByBeneficiaire(Beneficiaire beneficiaire);
    
    @Query("SELECT mb FROM ModuleBeneficiaire mb WHERE mb.module.chapitre.formation.idFormation = :formationId")
    List<ModuleBeneficiaire> findByFormationId(@Param("formationId") String formationId);
    
    @Query("SELECT mb FROM ModuleBeneficiaire mb WHERE mb.module.chapitre.idChapitre = :chapitreId")
    List<ModuleBeneficiaire> findByChapitreId(@Param("chapitreId") String chapitreId);
}
