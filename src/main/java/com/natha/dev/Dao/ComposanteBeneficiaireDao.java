package com.natha.dev.Dao;

import com.natha.dev.Model.ComposanteBeneficiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComposanteBeneficiaireDao extends JpaRepository<ComposanteBeneficiaire, Long> {

    List<ComposanteBeneficiaire> findByComposanteId(Long composanteId);
    
    List<ComposanteBeneficiaire> findByBeneficiaireIdBeneficiaire(String beneficiaireId);
    
    void deleteByComposanteIdAndBeneficiaireIdBeneficiaire(Long composanteId, String beneficiaireId);
}
