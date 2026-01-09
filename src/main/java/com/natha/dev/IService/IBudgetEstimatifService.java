package com.natha.dev.IService;

import com.natha.dev.Dto.BudgetEstimatifDTO;
import java.util.List;
import java.util.Optional;

public interface IBudgetEstimatifService {
    BudgetEstimatifDTO createBudgetEstimatif(BudgetEstimatifDTO budgetDTO);
    Optional<BudgetEstimatifDTO> getBudgetEstimatifById(Long id);
    List<BudgetEstimatifDTO> getAllBudgetEstimatif();
    List<BudgetEstimatifDTO> getBudgetEstimatifByProjet(String projetId);
    BudgetEstimatifDTO updateBudgetEstimatif(Long id, BudgetEstimatifDTO budgetDTO);
    void deleteBudgetEstimatif(Long id);
}
