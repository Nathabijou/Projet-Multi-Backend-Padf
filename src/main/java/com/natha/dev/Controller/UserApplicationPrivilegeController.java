package com.natha.dev.Controller;

import com.natha.dev.Dto.UserApplicationPrivilegeDto;
import com.natha.dev.IService.UserApplicationPrivilegeIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userAppPriv")
public class UserApplicationPrivilegeController {

    @Autowired
    private UserApplicationPrivilegeIService service;

    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/assign")
    public ResponseEntity<UserApplicationPrivilegeDto> assignPrivilege(@RequestBody UserApplicationPrivilegeDto dto) {
        UserApplicationPrivilegeDto saved = service.save(dto);
        return ResponseEntity.ok(saved);
    }
    //(Yes Ok)
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/all")
    public List<UserApplicationPrivilegeDto> getAll() {
        return service.findAll();
    }

    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/{userName}/{applicationId}/{privilegeName}")
    public ResponseEntity<UserApplicationPrivilegeDto> getById(
            @PathVariable String userName,
            @PathVariable String applicationId,
            @PathVariable String privilegeName) {

        Optional<UserApplicationPrivilegeDto> opt = service.findById(userName, applicationId, privilegeName);
        return opt.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('Admin')")
    @DeleteMapping("/{userName}/{applicationId}/{privilegeName}")
    public ResponseEntity<Void> delete(
            @PathVariable String userName,
            @PathVariable String applicationId,
            @PathVariable String privilegeName) {

        service.delete(userName, applicationId, privilegeName);
        return ResponseEntity.noContent().build();
    }
}
