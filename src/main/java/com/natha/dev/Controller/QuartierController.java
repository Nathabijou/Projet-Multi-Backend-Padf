package com.natha.dev.Controller;

import com.natha.dev.Dto.QuartierDto;
import com.natha.dev.IService.QuartierIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class QuartierController {

    @Autowired
    private QuartierIService service;

    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @PostMapping("/quartiers/create")
    public QuartierDto create(@RequestBody QuartierDto dto) {
        return service.save(dto);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/quartiers/section/{id}")
    public List<QuartierDto> getBySection(@PathVariable Long id) {
        return service.getBySectionCommunale(id);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/quartiers/all")
    public List<QuartierDto> all() {
        return service.getAll();
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/quartiers/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
