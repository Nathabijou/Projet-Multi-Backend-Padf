package com.natha.dev.Dao;

import com.natha.dev.Model.BudgetEstimatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetEstimatifDao extends JpaRepository<BudgetEstimatif, Long> {
    List<BudgetEstimatif> findByProjetIdProjet(String projetId);
    List<BudgetEstimatif> findByTenantId(String tenantId);
    List<BudgetEstimatif> findByProjetIdProjetAndTenantId(String projetId, String tenantId);
}
