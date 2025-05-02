package org.demo.student_management.controller;

import org.demo.student_management.services.implementations.AdminService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AdministratorController {

    private AdminService adminService;

    public AdministratorController(AdminService adminService) { this.adminService = adminService; }
//    @QueryMapping
//    public List<Administrator> administrators() {
//        return adminService.getAllAdministrators();
//    }
//
//    @QueryMapping
//    public Administrator administratorById(@Argument UUID id) {
//        return adminService.getAdministratorById(id);
//    }
//
//    @MutationMapping
//    public Administrator createAdministrator(@Argument Administrator admin) {
//        return adminService.createAdministrator(admin);
//    }
//
//    @MutationMapping
//    public Administrator updateAdministrator(@Argument UUID id, @Argument Administrator admin) {
//        return adminService.updateAdministrator(id, admin);
//    }
//
//    @MutationMapping
//    public boolean deleteAdministrator(@Argument UUID id) {
//        return adminService.deleteAdministrator(id);
//    }

}
