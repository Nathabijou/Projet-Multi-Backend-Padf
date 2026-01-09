package com.natha.dev.Controller;

import com.natha.dev.Dto.BudgetEstimatifDetailleDTO;
import com.natha.dev.IService.IBudgetEstimatifDetailleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budget-estimatif-detaille")
@CrossOrigin(origins = "*")
public class BudgetEstimatifDetailleController {

    @Autowired
    private IBudgetEstimatifDetailleService budgetEstimatifDetailleService;

    /**
     * Create a new Budget Estimatif Detaille
     */
    @PostMapping
    public ResponseEntity<BudgetEstimatifDetailleDTO> createBudgetEstimatifDetaille(@RequestBody BudgetEstimatifDetailleDTO detailleDTO) {
        BudgetEstimatifDetailleDTO createdDetaille = budgetEstimatifDetailleService.createBudgetEstimatifDetaille(detailleDTO);
        return new ResponseEntity<>(createdDetaille, HttpStatus.CREATED);
    }

    /**
     * Get Budget Estimatif Detaille by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<BudgetEstimatifDetailleDTO> getBudgetEstimatifDetailleById(@PathVariable Long id) {
        Optional<BudgetEstimatifDetailleDTO> detaille = budgetEstimatifDetailleService.getBudgetEstimatifDetailleById(id);
        return detaille.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all Budget Estimatif Detaille
     */
    @GetMapping
    public ResponseEntity<List<BudgetEstimatifDetailleDTO>> getAllBudgetEstimatifDetaille() {
        List<BudgetEstimatifDetailleDTO> detailles = budgetEstimatifDetailleService.getAllBudgetEstimatifDetaille();
        return new ResponseEntity<>(detailles, HttpStatus.OK);
    }

    /**
     * Get Budget Estimatif Detaille by Budget ID
     */
    @GetMapping("/budget/{budgetEstimatifId}")
    public ResponseEntity<List<BudgetEstimatifDetailleDTO>> getBudgetEstimatifDetailleByBudget(@PathVariable Long budgetEstimatifId) {
        List<BudgetEstimatifDetailleDTO> detailles = budgetEstimatifDetailleService.getBudgetEstimatifDetailleByBudget(budgetEstimatifId);
        return new ResponseEntity<>(detailles, HttpStatus.OK);
    }

    /**
     * Update Budget Estimatif Detaille
     */
    @PutMapping("/{id}")
    public ResponseEntity<BudgetEstimatifDetailleDTO> updateBudgetEstimatifDetaille(@PathVariable Long id, @RequestBody BudgetEstimatifDetailleDTO detailleDTO) {
        BudgetEstimatifDetailleDTO updatedDetaille = budgetEstimatifDetailleService.updateBudgetEstimatifDetaille(id, detailleDTO);
        if (updatedDetaille != null) {
            return new ResponseEntity<>(updatedDetaille, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete Budget Estimatif Detaille
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetEstimatifDetaille(@PathVariable Long id) {
        budgetEstimatifDetailleService.deleteBudgetEstimatifDetaille(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
