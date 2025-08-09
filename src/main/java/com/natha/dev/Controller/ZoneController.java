package com.natha.dev.Controller;

import com.natha.dev.Dto.ZoneDto;
import com.natha.dev.IService.ZoneIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ZoneController {

    @Autowired
    private ZoneIService service;

    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/zones/create")
    public ZoneDto create(@RequestBody ZoneDto dto) {
        return service.save(dto);
    }
   @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/zones/composante/{id}")
    public List<ZoneDto> getByComposante(@PathVariable Long id) {
        return service.getByComposanteId(id);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/zones/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PreAuthorize("hasAnyRole('Admin')")
    @GetMapping("/zones/all")
    public List<ZoneDto> all() {
        return service.getAll();
    }
}
