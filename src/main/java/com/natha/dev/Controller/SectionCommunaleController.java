package com.natha.dev.Controller;

import com.natha.dev.Dto.SectionCommunaleDto;
import com.natha.dev.IService.SectionCommunaleIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SectionCommunaleController {

    @Autowired
    private SectionCommunaleIService service;
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/sections/create")
    public SectionCommunaleDto create(@RequestBody SectionCommunaleDto dto) {
        return service.save(dto);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User','Moderant')")
    @GetMapping("/sections/commune/{id}")
    public List<SectionCommunaleDto> getByCommune(@PathVariable Long id) {
        return service.getByCommune(id);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager', 'User')")
    @GetMapping("/sections/all")
    public List<SectionCommunaleDto> all() {
        return service.getAll();
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/sections/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

