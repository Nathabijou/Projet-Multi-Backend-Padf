package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.BudgetEstimatifDao;
import com.natha.dev.Dao.BudgetEstimatifDetailleDao;
import com.natha.dev.Dto.BudgetEstimatifDetailleDTO;
import com.natha.dev.IService.IBudgetEstimatifDetailleService;
import com.natha.dev.Model.BudgetEstimatif;
import com.natha.dev.Model.BudgetEstimatifDetaille;
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
public class BudgetEstimatifDetailleServiceImpl implements IBudgetEstimatifDetailleService {

    @Autowired
    private BudgetEstimatifDetailleDao budgetEstimatifDetailleDao;

    @Autowired
    private BudgetEstimatifDao budgetEstimatifDao;

    @Override
    public BudgetEstimatifDetailleDTO createBudgetEstimatifDetaille(BudgetEstimatifDetailleDTO detailleDTO) {
        BudgetEstimatifDetaille detaille = new BudgetEstimatifDetaille();

        // Get the BudgetEstimatif by ID
        Optional<BudgetEstimatif> budgetOptional = budgetEstimatifDao.findById(detailleDTO.getBudgetEstimatifId());
        if (!budgetOptional.isPresent()) {
            throw new IllegalArgumentException("BudgetEstimatif not found with ID: " + detailleDTO.getBudgetEstimatifId());
        }

        detaille.setBudgetEstimatif(budgetOptional.get());
        detaille.setTitre(detailleDTO.getTitre());
        detaille.setDescription(detailleDTO.getDescription());
        detaille.setQuantite(detailleDTO.getQuantite());
        detaille.setCoutUnitaire(detailleDTO.getCoutUnitaire());
        detaille.setCoutTotal(detailleDTO.getQuantite() * detailleDTO.getCoutUnitaire());
        detaille.setCreatedBy(detailleDTO.getCreatedBy());
        detaille.setCreateDate(LocalDateTime.now());
        detaille.setModifyDate(LocalDateTime.now());
        detaille.setTenantId(getTenantId());

        BudgetEstimatifDetaille savedDetaille = budgetEstimatifDetailleDao.save(detaille);
        return convertToDTO(savedDetaille);
    }

    @Override
    public Optional<BudgetEstimatifDetailleDTO> getBudgetEstimatifDetailleById(Long id) {
        return budgetEstimatifDetailleDao.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<BudgetEstimatifDetailleDTO> getAllBudgetEstimatifDetaille() {
        return budgetEstimatifDetailleDao.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BudgetEstimatifDetailleDTO> getBudgetEstimatifDetailleByBudget(Long budgetEstimatifId) {
        return budgetEstimatifDetailleDao.findByBudgetEstimatifId(budgetEstimatifId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BudgetEstimatifDetailleDTO updateBudgetEstimatifDetaille(Long id, BudgetEstimatifDetailleDTO detailleDTO) {
        Optional<BudgetEstimatifDetaille> detailleOptional = budgetEstimatifDetailleDao.findById(id);
        if (detailleOptional.isPresent()) {
            BudgetEstimatifDetaille detaille = detailleOptional.get();
            detaille.setTitre(detailleDTO.getTitre());
            detaille.setDescription(detailleDTO.getDescription());
            detaille.setQuantite(detailleDTO.getQuantite());
            detaille.setCoutUnitaire(detailleDTO.getCoutUnitaire());
            detaille.setCoutTotal(detailleDTO.getQuantite() * detailleDTO.getCoutUnitaire());
            detaille.setModifyBy(getUsername());
            detaille.setModifyDate(LocalDateTime.now());

            BudgetEstimatifDetaille updatedDetaille = budgetEstimatifDetailleDao.save(detaille);
            return convertToDTO(updatedDetaille);
        }
        return null;
    }

    @Override
    public void deleteBudgetEstimatifDetaille(Long id) {
        budgetEstimatifDetailleDao.deleteById(id);
    }

    /**
     * Convert BudgetEstimatifDetaille entity to BudgetEstimatifDetailleDTO
     */
    private BudgetEstimatifDetailleDTO convertToDTO(BudgetEstimatifDetaille detaille) {
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
