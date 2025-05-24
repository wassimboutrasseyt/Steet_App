package org.demo.student_management.controller;

import org.demo.student_management.entities.Administrator;
import org.demo.student_management.services.implementations.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
public class AdministratorController {

    private final AdminService adminService;

    public AdministratorController(AdminService adminService) { 
        this.adminService = adminService; 
    }
    
    @GetMapping
    public ResponseEntity<List<Administrator>> getAllAdministrators() {
        return ResponseEntity.ok(adminService.getAllAdministrators());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.getAdministratorById(id));
    }
    
    @PostMapping
    public ResponseEntity<Administrator> createAdministrator(@RequestBody Administrator admin) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.createAdministrator(admin));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(
            @PathVariable UUID id, 
            @RequestBody Administrator admin) {
        return ResponseEntity.ok(adminService.updateAdministrator(id, admin));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdministrator(@PathVariable UUID id) {
        return ResponseEntity.ok(adminService.deleteAdministrator(id));
    }
}
