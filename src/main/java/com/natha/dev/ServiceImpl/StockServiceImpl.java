package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.StockDao;
import com.natha.dev.Dto.StockDTO;
import com.natha.dev.IService.IStockService;
import com.natha.dev.Model.Stock;
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
public class StockServiceImpl implements IStockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = new Stock();
        stock.setName(stockDTO.getName());
        stock.setDescription(stockDTO.getDescription());
        stock.setType(Stock.StockType.valueOf(stockDTO.getType()));
        stock.setComposanteId(stockDTO.getComposanteId());
        stock.setProjetId(stockDTO.getProjetId());
        stock.setAddress(stockDTO.getAddress());
        stock.setManagerId(stockDTO.getManagerId());
        stock.setStatus(Stock.StockStatus.valueOf(stockDTO.getStatus()));
        stock.setGestionType(Stock.GestionType.valueOf(stockDTO.getGestionType()));
        stock.setEvaluationMethod(Stock.EvaluationMethod.valueOf(stockDTO.getEvaluationMethod()));
        stock.setTaxRate(stockDTO.getTaxRate());
        stock.setCurrency(stockDTO.getCurrency());
        stock.setShareable(stockDTO.getShareable());
        stock.setTemporary(stockDTO.getTemporary());
        stock.setCreatedBy(stockDTO.getCreatedBy());
        stock.setUpdatedBy(stockDTO.getUpdatedBy());
        stock.setQuantite(stockDTO.getQuantite());
        stock.setUniteMesure(stockDTO.getUniteMesure());
        stock.setPrixUnitaireUsd(stockDTO.getPrixUnitaireUsd());
        stock.setPrixUnitaireHtg(stockDTO.getPrixUnitaireHtg());
        stock.setTitre(stockDTO.getTitre());
        stock.setCreateDate(LocalDateTime.now());
        stock.setUpdateDate(LocalDateTime.now());
        stock.setTenantId(getTenantId());

        Stock savedStock = stockDao.save(stock);
        return convertToDTO(savedStock);
    }

    @Override
    public Optional<StockDTO> getStockById(Long id) {
        return stockDao.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<StockDTO> getAllStocks() {
        return stockDao.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getStocksByTenant(String tenantId) {
        return stockDao.findByTenantId(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StockDTO updateStock(Long id, StockDTO stockDTO) {
        Optional<Stock> stockOptional = stockDao.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setName(stockDTO.getName());
            stock.setDescription(stockDTO.getDescription());
            stock.setType(Stock.StockType.valueOf(stockDTO.getType()));
            stock.setComposanteId(stockDTO.getComposanteId());
            stock.setProjetId(stockDTO.getProjetId());
            stock.setAddress(stockDTO.getAddress());
            stock.setManagerId(stockDTO.getManagerId());
            stock.setStatus(Stock.StockStatus.valueOf(stockDTO.getStatus()));
            stock.setGestionType(Stock.GestionType.valueOf(stockDTO.getGestionType()));
            stock.setEvaluationMethod(Stock.EvaluationMethod.valueOf(stockDTO.getEvaluationMethod()));
            stock.setTaxRate(stockDTO.getTaxRate());
            stock.setCurrency(stockDTO.getCurrency());
            stock.setShareable(stockDTO.getShareable());
            stock.setTemporary(stockDTO.getTemporary());
            stock.setUpdatedBy(stockDTO.getUpdatedBy());
            stock.setQuantite(stockDTO.getQuantite());
            stock.setUniteMesure(stockDTO.getUniteMesure());
            stock.setPrixUnitaireUsd(stockDTO.getPrixUnitaireUsd());
            stock.setPrixUnitaireHtg(stockDTO.getPrixUnitaireHtg());
            stock.setTitre(stockDTO.getTitre());
            stock.setUpdateDate(LocalDateTime.now());

            Stock updatedStock = stockDao.save(stock);
            return convertToDTO(updatedStock);
        }
        return null;
    }

    @Override
    public void deleteStock(Long id) {
        stockDao.deleteById(id);
    }

    @Override
    public List<StockDTO> getStocksByName(String name) {
        return stockDao.findByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getStocksByType(String type) {
        return stockDao.findByType(Stock.StockType.valueOf(type)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getStocksByStatus(String status) {
        return stockDao.findByStatus(Stock.StockStatus.valueOf(status)).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getStocksByComposante(Long composanteId) {
        return stockDao.findByComposanteId(composanteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockDTO> getStocksByProjet(String projetId) {
        return stockDao.findByProjetId(projetId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StockDTO updateStockStatus(Long id, String status) {
        Optional<Stock> stockOptional = stockDao.findById(id);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setStatus(Stock.StockStatus.valueOf(status));
            stock.setUpdateDate(LocalDateTime.now());
            Stock updatedStock = stockDao.save(stock);
            return convertToDTO(updatedStock);
        }
        return null;
    }

    /**
     * Konvèti Stock entity a StockDTO
     */
    private StockDTO convertToDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setDescription(stock.getDescription());
        dto.setType(stock.getType().toString());
        dto.setComposanteId(stock.getComposanteId());
        dto.setProjetId(stock.getProjetId());
        dto.setAddress(stock.getAddress());
        dto.setManagerId(stock.getManagerId());
        dto.setCreateDate(stock.getCreateDate());
        dto.setUpdateDate(stock.getUpdateDate());
        dto.setStatus(stock.getStatus().toString());
        dto.setGestionType(stock.getGestionType().toString());
        dto.setEvaluationMethod(stock.getEvaluationMethod().toString());
        dto.setTaxRate(stock.getTaxRate());
        dto.setCurrency(stock.getCurrency());
        dto.setShareable(stock.getShareable());
        dto.setTemporary(stock.getTemporary());
        dto.setCreatedBy(stock.getCreatedBy());
        dto.setUpdatedBy(stock.getUpdatedBy());
        dto.setQuantite(stock.getQuantite());
        dto.setUniteMesure(stock.getUniteMesure());
        dto.setPrixUnitaireUsd(stock.getPrixUnitaireUsd());
        dto.setPrixUnitaireHtg(stock.getPrixUnitaireHtg());
        dto.setTitre(stock.getTitre());
        return dto;
    }

    /**
     * Jwenn tenant_id soti nan authenticated user la
     */
    private String getTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            // Pou kounye a, nou itilize username kòm tenant_id
            // Ou ka modifye sa a pou jwenn tenant_id soti nan yon table oswa context
            return username != null ? username : "default-tenant";
        }
        return "default-tenant";
    }
}
