package com.natha.dev.IService;

import com.natha.dev.Dto.BudgetEstimatifDetailleDTO;
import java.util.List;
import java.util.Optional;

public interface IBudgetEstimatifDetailleService {
    BudgetEstimatifDetailleDTO createBudgetEstimatifDetaille(BudgetEstimatifDetailleDTO detailleDTO);
    Optional<BudgetEstimatifDetailleDTO> getBudgetEstimatifDetailleById(Long id);
    List<BudgetEstimatifDetailleDTO> getAllBudgetEstimatifDetaille();
    List<BudgetEstimatifDetailleDTO> getBudgetEstimatifDetailleByBudget(Long budgetEstimatifId);
    BudgetEstimatifDetailleDTO updateBudgetEstimatifDetaille(Long id, BudgetEstimatifDetailleDTO detailleDTO);
    void deleteBudgetEstimatifDetaille(Long id);
}
