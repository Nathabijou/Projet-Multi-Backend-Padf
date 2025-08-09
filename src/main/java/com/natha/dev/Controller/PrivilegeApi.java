package com.natha.dev.Controller;

import com.natha.dev.Model.Privilege;
import com.natha.dev.ServiceImpl.PrivilegeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/privileges")
public class PrivilegeApi {

    @Autowired
    private PrivilegeImpl privilegeImpl;

    // API pou kreye privilèj
    @PreAuthorize("hasAnyRole('Admin')")
    @PostMapping("/create")
    public Privilege createNewPrivilege(@RequestBody Privilege privilege) {
        return privilegeImpl.createNewPrivilege(privilege);
    }

    // API pou lis tout privilèj
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/all")
    public List<Privilege> getAllPrivileges() {
        return privilegeImpl.getAllPrivileges();
    }

    // API pou efase yon privilèj
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/delete/{name}")
    public void deletePrivilege(@PathVariable String name) {
        privilegeImpl.deletePrivilege(name);
    }

    // API pou tcheke/kreye otomatikman
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/getOrCreate")
    public Privilege getOrCreatePrivilege(@RequestBody Privilege privilege) {
        return privilegeImpl.getOrCreatePrivilege(privilege.getPrivilegeName(), privilege.getPrivilegeDescription());
    }

    // API pou bay yon privilèj a yon itilizatè
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @PostMapping("/assign-user-privilege")
    public ResponseEntity<?> assignUserPrivilege(
            @RequestParam String userName,
            @RequestParam String componentName,
            @RequestParam(required = false) String elementName,
            @RequestParam String privilegeName) {
        try {
            return ResponseEntity.ok(privilegeImpl.assignUserPrivilege(userName, componentName, elementName, privilegeName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API pou retire yon privilèj yon itilizatè
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @DeleteMapping("/remove-user-privilege")
    public ResponseEntity<?> removeUserPrivilege(
            @RequestParam String userName,
            @RequestParam String componentName,
            @RequestParam(required = false) String elementName,
            @RequestParam String privilegeName) {
        try {
            privilegeImpl.removeUserPrivilege(userName, componentName, elementName, privilegeName);
            return ResponseEntity.ok("Privillege removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API pou tcheke si yon itilizatè gen yon privilèj
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/check-user-privilege")
    public ResponseEntity<?> checkUserPrivilege(
            @RequestParam String userName,
            @RequestParam String componentName,
            @RequestParam(required = false) String elementName,
            @RequestParam String privilegeName) {
        try {
            boolean hasPrivilege = privilegeImpl.checkUserPrivilege(userName, componentName, elementName, privilegeName);
            return ResponseEntity.ok(hasPrivilege);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API pou jwenn tout privilèj yon itilizatè
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @GetMapping("/get-user-privileges")
    public ResponseEntity<?> getUserPrivileges(
            @RequestParam String userName,
            @RequestParam(required = false) String componentName) {
        try {
            return ResponseEntity.ok(privilegeImpl.getUserPrivileges(userName, componentName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
