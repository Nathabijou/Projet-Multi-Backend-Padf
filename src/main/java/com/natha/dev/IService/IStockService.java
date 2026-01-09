package com.natha.dev.IService;

import com.natha.dev.Dto.StockDTO;
import java.util.List;
import java.util.Optional;

public interface IStockService {

    /**
     * Kreye yon stock nouvo
     */
    StockDTO createStock(StockDTO stockDTO);

    /**
     * Jwenn stock pa id
     */
    Optional<StockDTO> getStockById(Long id);

    /**
     * Jwenn tout stock
     */
    List<StockDTO> getAllStocks();

    /**
     * Jwenn tout stock pou yon tenant
     */
    List<StockDTO> getStocksByTenant(String tenantId);

    /**
     * Mete a jou stock
     */
    StockDTO updateStock(Long id, StockDTO stockDTO);

    /**
     * Efase stock
     */
    void deleteStock(Long id);

    /**
     * Jwenn stock pa non
     */
    List<StockDTO> getStocksByName(String name);

    /**
     * Jwenn stock pa tip
     */
    List<StockDTO> getStocksByType(String type);

    /**
     * Jwenn stock pa status
     */
    List<StockDTO> getStocksByStatus(String status);

    /**
     * Jwenn stock pou yon composante
     */
    List<StockDTO> getStocksByComposante(Long composanteId);

    /**
     * Jwenn stock pou yon projet
     */
    List<StockDTO> getStocksByProjet(String projetId);

    /**
     * Chanje status stock
     */
    StockDTO updateStockStatus(Long id, String status);
}
