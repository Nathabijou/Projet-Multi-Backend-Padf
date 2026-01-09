package com.natha.dev.Controller;

import com.natha.dev.Dto.StockItemDTO;
import com.natha.dev.Dto.MouvementStockDTO;
import com.natha.dev.IService.IStockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock-items")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StockItemController {

    @Autowired
    private IStockItemService stockItemService;

    /**
     * Kreye yon stock item
     */
    @PostMapping
    public ResponseEntity<StockItemDTO> createStockItem(@RequestBody StockItemDTO stockItemDTO) {
        StockItemDTO createdStockItem = stockItemService.createStockItem(stockItemDTO);
        if (createdStockItem != null) {
            return new ResponseEntity<>(createdStockItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Mete a jou yon stock item
     */
    @PutMapping("/{id}")
    public ResponseEntity<StockItemDTO> updateStockItem(@PathVariable Long id, @RequestBody StockItemDTO stockItemDTO) {
        StockItemDTO updatedStockItem = stockItemService.updateStockItem(id, stockItemDTO);
        if (updatedStockItem != null) {
            return new ResponseEntity<>(updatedStockItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn stock item pa id
     */
    @GetMapping("/{id}")
    public ResponseEntity<StockItemDTO> getStockItemById(@PathVariable Long id) {
        Optional<StockItemDTO> stockItem = stockItemService.getStockItemById(id);
        if (stockItem.isPresent()) {
            return new ResponseEntity<>(stockItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn stock item pa stock id
     */
    @GetMapping("/stock/{stockId}")
    public ResponseEntity<List<StockItemDTO>> getStockItemsByStockId(@PathVariable Long stockId) {
        List<StockItemDTO> stockItems = stockItemService.getStockItemsByStockId(stockId);
        return new ResponseEntity<>(stockItems, HttpStatus.OK);
    }

    /**
     * Jwenn stock item pa article id
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<StockItemDTO>> getStockItemsByArticleId(@PathVariable Long articleId) {
        List<StockItemDTO> stockItems = stockItemService.getStockItemsByArticleId(articleId);
        return new ResponseEntity<>(stockItems, HttpStatus.OK);
    }

    /**
     * Jwenn stock item pa stock ak article
     */
    @GetMapping("/search")
    public ResponseEntity<StockItemDTO> getStockItemByStockAndArticle(
            @RequestParam Long stockId,
            @RequestParam Long articleId) {
        Optional<StockItemDTO> stockItem = stockItemService.getStockItemByStockAndArticle(stockId, articleId);
        if (stockItem.isPresent()) {
            return new ResponseEntity<>(stockItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn stock item pa etat
     */
    @GetMapping("/search/etat")
    public ResponseEntity<List<StockItemDTO>> getStockItemsByEtat(@RequestParam String etat) {
        List<StockItemDTO> stockItems = stockItemService.getStockItemsByEtat(etat);
        return new ResponseEntity<>(stockItems, HttpStatus.OK);
    }

    /**
     * Jwenn tout stock item
     */
    @GetMapping
    public ResponseEntity<List<StockItemDTO>> getAllStockItems() {
        List<StockItemDTO> stockItems = stockItemService.getAllStockItems();
        return new ResponseEntity<>(stockItems, HttpStatus.OK);
    }

    /**
     * Jwenn stock item kote quantite < seuil minimum
     */
    @GetMapping("/search/low-stock")
    public ResponseEntity<List<StockItemDTO>> getLowStockItems() {
        List<StockItemDTO> stockItems = stockItemService.getLowStockItems();
        return new ResponseEntity<>(stockItems, HttpStatus.OK);
    }

    /**
     * Ajoute mouvman entree
     */
    @PostMapping("/{id}/entree")
    public ResponseEntity<StockItemDTO> addEntree(
            @PathVariable Long id,
            @RequestParam Double quantite,
            @RequestParam Double prixUnitaire,
            @RequestParam(required = false) String description,
            @RequestParam Long createdBy) {
        StockItemDTO updatedStockItem = stockItemService.addEntree(id, quantite, prixUnitaire, description, createdBy);
        if (updatedStockItem != null) {
            return new ResponseEntity<>(updatedStockItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Ajoute mouvman sortie
     */
    @PostMapping("/{id}/sortie")
    public ResponseEntity<StockItemDTO> addSortie(
            @PathVariable Long id,
            @RequestParam Double quantite,
            @RequestParam(required = false) String description,
            @RequestParam Long createdBy) {
        StockItemDTO updatedStockItem = stockItemService.addSortie(id, quantite, description, createdBy);
        if (updatedStockItem != null) {
            return new ResponseEntity<>(updatedStockItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Ajoute mouvman ajusteman
     */
    @PostMapping("/{id}/ajustement")
    public ResponseEntity<StockItemDTO> addAjustement(
            @PathVariable Long id,
            @RequestParam Double quantite,
            @RequestParam(required = false) String description,
            @RequestParam Long createdBy) {
        StockItemDTO updatedStockItem = stockItemService.addAjustement(id, quantite, description, createdBy);
        if (updatedStockItem != null) {
            return new ResponseEntity<>(updatedStockItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Jwenn mouvman pou yon stock item
     */
    @GetMapping("/{id}/mouvements")
    public ResponseEntity<List<MouvementStockDTO>> getMouvementsByStockItem(@PathVariable Long id) {
        List<MouvementStockDTO> mouvements = stockItemService.getMouvementsByStockItem(id);
        return new ResponseEntity<>(mouvements, HttpStatus.OK);
    }

    /**
     * Efase yon stock item
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockItem(@PathVariable Long id) {
        stockItemService.deleteStockItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
