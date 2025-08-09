package com.natha.dev.Controller;

import com.natha.dev.Model.Role;
import com.natha.dev.ServiceImpl.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/createNewRole")
    public Role createNewRole(@RequestBody String roleName) {
        return roleService.createNewRoleWithPrivileges(roleName);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}
