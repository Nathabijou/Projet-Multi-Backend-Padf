package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BudgetEstimatifDao;
import com.natha.dev.Dto.BudgetEstimatifDTO;
import com.natha.dev.Dto.BudgetEstimatifDetailleDTO;
import com.natha.dev.IService.IBudgetEstimatifService;
import com.natha.dev.Model.BudgetEstimatif;
import com.natha.dev.Model.BudgetEstimatifDetaille;
import com.natha.dev.Model.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BudgetEstimatifServiceImpl implements IBudgetEstimatifService {

    @Autowired
    private BudgetEstimatifDao budgetEstimatifDao;

    @Override
    public BudgetEstimatifDTO createBudgetEstimatif(BudgetEstimatifDTO budgetDTO) {
        BudgetEstimatif budget = new BudgetEstimatif();
        
        // Create a Projet object with the ID from DTO
        Projet projet = new Projet();
        projet.setIdProjet(budgetDTO.getProjetId());
        
        budget.setProjet(projet);
        budget.setTitre(budgetDTO.getTitre());
        budget.setDescription(budgetDTO.getDescription());
        budget.setQuantite(budgetDTO.getQuantite());
        budget.setUnite(budgetDTO.getUnite());
        budget.setPrixUnitaireGourdes(budgetDTO.getPrixUnitaireGourdes());
        budget.setTotalPrixGourdes(budgetDTO.getQuantite() * budgetDTO.getPrixUnitaireGourdes());
        budget.setCreatedBy(budgetDTO.getCreatedBy());
        budget.setCreateDate(LocalDateTime.now());
        budget.setModifyDate(LocalDateTime.now());
        budget.setTenantId(getTenantId());

        BudgetEstimatif savedBudget = budgetEstimatifDao.save(budget);
        return convertToDTO(savedBudget);
    }

    @Override
    public Optional<BudgetEstimatifDTO> getBudgetEstimatifById(Long id) {
        return budgetEstimatifDao.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<BudgetEstimatifDTO> getAllBudgetEstimatif() {
        return budgetEstimatifDao.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetEstimatifDTO> getBudgetEstimatifByProjet(String projetId) {
        return budgetEstimatifDao.findByProjetIdProjet(projetId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetEstimatifDTO updateBudgetEstimatif(Long id, BudgetEstimatifDTO budgetDTO) {
        Optional<BudgetEstimatif> budgetOptional = budgetEstimatifDao.findById(id);
        if (budgetOptional.isPresent()) {
            BudgetEstimatif budget = budgetOptional.get();
            budget.setTitre(budgetDTO.getTitre());
            budget.setDescription(budgetDTO.getDescription());
            budget.setQuantite(budgetDTO.getQuantite());
            budget.setUnite(budgetDTO.getUnite());
            budget.setPrixUnitaireGourdes(budgetDTO.getPrixUnitaireGourdes());
            budget.setTotalPrixGourdes(budgetDTO.getQuantite() * budgetDTO.getPrixUnitaireGourdes());
            budget.setModifyBy(getUsername());
            budget.setModifyDate(LocalDateTime.now());

            BudgetEstimatif updatedBudget = budgetEstimatifDao.save(budget);
            return convertToDTO(updatedBudget);
        }
        return null;
    }

    @Override
    public void deleteBudgetEstimatif(Long id) {
        budgetEstimatifDao.deleteById(id);
    }

    /**
     * Convert BudgetEstimatif entity to BudgetEstimatifDTO
     */
    private BudgetEstimatifDTO convertToDTO(BudgetEstimatif budget) {
        BudgetEstimatifDTO dto = new BudgetEstimatifDTO();
        dto.setId(budget.getId());
        dto.setProjetId(budget.getProjet().getIdProjet());
        dto.setTitre(budget.getTitre());
        dto.setDescription(budget.getDescription());
        dto.setQuantite(budget.getQuantite());
        dto.setUnite(budget.getUnite());
        dto.setPrixUnitaireGourdes(budget.getPrixUnitaireGourdes());
        dto.setTotalPrixGourdes(budget.getTotalPrixGourdes());
        dto.setCreateDate(budget.getCreateDate());
        dto.setCreatedBy(budget.getCreatedBy());
        dto.setModifyDate(budget.getModifyDate());
        dto.setModifyBy(budget.getModifyBy());
        
        // Convert detailles
        if (budget.getDetailles() != null && !budget.getDetailles().isEmpty()) {
            List<BudgetEstimatifDetailleDTO> detaillesDTOs = budget.getDetailles().stream()
                    .map(this::convertDetailleToDTO)
                    .collect(Collectors.toList());
            dto.setDetailles(detaillesDTOs);
        }
        
        return dto;
    }

    /**
     * Convert BudgetEstimatifDetaille entity to BudgetEstimatifDetailleDTO
     */
    private BudgetEstimatifDetailleDTO convertDetailleToDTO(BudgetEstimatifDetaille detaille) {
        BudgetEstimatifDetailleDTO dto = new BudgetEstimatifDetailleDTO();
        dto.setId(detaille.getId());
        dto.setBudgetEstimatifId(detaille.getBudgetEstimatif().getId());
        dto.setTitre(detaille.getTitre());
        dto.setDescription(detaille.getDescription());
        dto.setQuantite(detaille.getQuantite());
        dto.setCoutUnitaire(detaille.getCoutUnitaire());
        dto.setCoutTotal(detaille.getCoutTotal());
        dto.setCreateDate(detaille.getCreateDate());
        dto.setCreatedBy(detaille.getCreatedBy());
        dto.setModifyDate(detaille.getModifyDate());
        dto.setModifyBy(detaille.getModifyBy());
        return dto;
    }

    /**
     * Get tenant ID from authenticated user
     */
    private String getTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return username != null ? username : "default-tenant";
        }
        return "default-tenant";
    }

    /**
     * Get username from authenticated user
     */
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "system";
    }
}
