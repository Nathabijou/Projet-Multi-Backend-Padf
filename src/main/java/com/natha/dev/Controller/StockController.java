package com.natha.dev.Controller;

import com.natha.dev.Dto.StockDTO;
import com.natha.dev.IService.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private IStockService stockService;

    /**
     * Kreye yon stock nouvo
     */
    @PostMapping
    public ResponseEntity<StockDTO> createStock(@RequestBody StockDTO stockDTO) {
        StockDTO createdStock = stockService.createStock(stockDTO);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    /**
     * Jwenn stock pa id
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id) {
        Optional<StockDTO> stock = stockService.getStockById(id);
        return stock.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Jwenn tout stock
     */
    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockDTO> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Jwenn tout stock pou yon tenant
     */
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<StockDTO>> getStocksByTenant(@PathVariable String tenantId) {
        List<StockDTO> stocks = stockService.getStocksByTenant(tenantId);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Mete a jou stock
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockDTO stockDTO) {
        StockDTO updatedStock = stockService.updateStock(id, stockDTO);
        if (updatedStock != null) {
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Efase stock
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Jwenn stock pa non
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<StockDTO>> getStocksByName(@RequestParam String name) {
        List<StockDTO> stocks = stockService.getStocksByName(name);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Jwenn stock pa tip
     */
    @GetMapping("/search/type")
    public ResponseEntity<List<StockDTO>> getStocksByType(@RequestParam String type) {
        List<StockDTO> stocks = stockService.getStocksByType(type);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Jwenn stock pa status
     */
    @GetMapping("/search/status")
    public ResponseEntity<List<StockDTO>> getStocksByStatus(@RequestParam String status) {
        List<StockDTO> stocks = stockService.getStocksByStatus(status);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Jwenn stock pou yon composante
     */
    @GetMapping("/composante/{composanteId}")
    public ResponseEntity<List<StockDTO>> getStocksByComposante(@PathVariable Long composanteId) {
        List<StockDTO> stocks = stockService.getStocksByComposante(composanteId);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Jwenn stock pou yon projet
     */
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<StockDTO>> getStocksByProjet(@PathVariable String projetId) {
        List<StockDTO> stocks = stockService.getStocksByProjet(projetId);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    /**
     * Chanje status stock
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<StockDTO> updateStockStatus(@PathVariable Long id, @RequestParam String status) {
        StockDTO updatedStock = stockService.updateStockStatus(id, status);
        if (updatedStock != null) {
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ============ METÒD ESPESYALIZE POU COMPOSANTE ============

    /**
     * Kreye yon stock pou yon composante espesifik
     */
    @PostMapping("/composante/{composanteId}")
    public ResponseEntity<StockDTO> createStockForComposante(
            @PathVariable Long composanteId,
            @RequestBody StockDTO stockDTO) {
        stockDTO.setComposanteId(composanteId);
        StockDTO createdStock = stockService.createStock(stockDTO);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    /**
     * Mete a jou stock nan yon composante espesifik
     */
    @PutMapping("/composante/{composanteId}/stock/{stockId}")
    public ResponseEntity<StockDTO> updateStockInComposante(
            @PathVariable Long composanteId,
            @PathVariable Long stockId,
            @RequestBody StockDTO stockDTO) {
        Optional<StockDTO> existingStock = stockService.getStockById(stockId);
        if (existingStock.isPresent() && existingStock.get().getComposanteId().equals(composanteId)) {
            stockDTO.setComposanteId(composanteId);
            StockDTO updatedStock = stockService.updateStock(stockId, stockDTO);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Efase stock nan yon composante espesifik
     */
    @DeleteMapping("/composante/{composanteId}/stock/{stockId}")
    public ResponseEntity<Void> deleteStockInComposante(
            @PathVariable Long composanteId,
            @PathVariable Long stockId) {
        Optional<StockDTO> stock = stockService.getStockById(stockId);
        if (stock.isPresent() && stock.get().getComposanteId().equals(composanteId)) {
            stockService.deleteStock(stockId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ============ METÒD ESPESYALIZE POU PROJET ============

    /**
     * Kreye yon stock pou yon projet espesifik
     */
    @PostMapping("/projet/{projetId}")
    public ResponseEntity<StockDTO> createStockForProjet(
            @PathVariable String projetId,
            @RequestBody StockDTO stockDTO) {
        stockDTO.setProjetId(projetId);
        StockDTO createdStock = stockService.createStock(stockDTO);
        return new ResponseEntity<>(createdStock, HttpStatus.CREATED);
    }

    /**
     * Mete a jou stock nan yon projet espesifik
     */
    @PutMapping("/projet/{projetId}/stock/{stockId}")
    public ResponseEntity<StockDTO> updateStockInProjet(
            @PathVariable String projetId,
            @PathVariable Long stockId,
            @RequestBody StockDTO stockDTO) {
        Optional<StockDTO> existingStock = stockService.getStockById(stockId);
        if (existingStock.isPresent() && existingStock.get().getProjetId().equals(projetId)) {
            stockDTO.setProjetId(projetId);
            StockDTO updatedStock = stockService.updateStock(stockId, stockDTO);
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Efase stock nan yon projet espesifik
     */
    @DeleteMapping("/projet/{projetId}/stock/{stockId}")
    public ResponseEntity<Void> deleteStockInProjet(
            @PathVariable String projetId,
            @PathVariable Long stockId) {
        Optional<StockDTO> stock = stockService.getStockById(stockId);
        if (stock.isPresent() && stock.get().getProjetId().equals(projetId)) {
            stockService.deleteStock(stockId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
