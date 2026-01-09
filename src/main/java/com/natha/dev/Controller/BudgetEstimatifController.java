package com.natha.dev.Controller;

import com.natha.dev.Dto.BudgetEstimatifDTO;
import com.natha.dev.IService.IBudgetEstimatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budget-estimatif")
@CrossOrigin(origins = "*")
public class BudgetEstimatifController {

    @Autowired
    private IBudgetEstimatifService budgetEstimatifService;

    /**
     * Create a new Budget Estimatif
     */
    @PostMapping
    public ResponseEntity<BudgetEstimatifDTO> createBudgetEstimatif(@RequestBody BudgetEstimatifDTO budgetDTO) {
        BudgetEstimatifDTO createdBudget = budgetEstimatifService.createBudgetEstimatif(budgetDTO);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    /**
     * Get Budget Estimatif by ID with details
     */
    @GetMapping("/{id}")
    public ResponseEntity<BudgetEstimatifDTO> getBudgetEstimatifById(@PathVariable Long id) {
        Optional<BudgetEstimatifDTO> budget = budgetEstimatifService.getBudgetEstimatifById(id);
        return budget.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get Budget Estimatif by ID with all details
     */
    @GetMapping("/{id}/with-details")
    public ResponseEntity<BudgetEstimatifDTO> getBudgetEstimatifWithDetails(@PathVariable Long id) {
        Optional<BudgetEstimatifDTO> budget = budgetEstimatifService.getBudgetEstimatifById(id);
        return budget.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all Budget Estimatif
     */
    @GetMapping
    public ResponseEntity<List<BudgetEstimatifDTO>> getAllBudgetEstimatif() {
        List<BudgetEstimatifDTO> budgets = budgetEstimatifService.getAllBudgetEstimatif();
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    /**
     * Get Budget Estimatif by Projet ID
     */
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<BudgetEstimatifDTO>> getBudgetEstimatifByProjet(@PathVariable String projetId) {
        List<BudgetEstimatifDTO> budgets = budgetEstimatifService.getBudgetEstimatifByProjet(projetId);
        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }

    /**
     * Update Budget Estimatif
     */
    @PutMapping("/{id}")
    public ResponseEntity<BudgetEstimatifDTO> updateBudgetEstimatif(@PathVariable Long id, @RequestBody BudgetEstimatifDTO budgetDTO) {
        BudgetEstimatifDTO updatedBudget = budgetEstimatifService.updateBudgetEstimatif(id, budgetDTO);
        if (updatedBudget != null) {
            return new ResponseEntity<>(updatedBudget, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete Budget Estimatif
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetEstimatif(@PathVariable Long id) {
        budgetEstimatifService.deleteBudgetEstimatif(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
