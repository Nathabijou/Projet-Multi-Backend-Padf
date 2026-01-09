package com.natha.dev.Dao;

import com.natha.dev.Model.BudgetEstimatifDetaille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetEstimatifDetailleDao extends JpaRepository<BudgetEstimatifDetaille, Long> {
    List<BudgetEstimatifDetaille> findByBudgetEstimatifId(Long budgetEstimatifId);
    List<BudgetEstimatifDetaille> findByTenantId(String tenantId);
    List<BudgetEstimatifDetaille> findByBudgetEstimatifIdAndTenantId(Long budgetEstimatifId, String tenantId);
}
