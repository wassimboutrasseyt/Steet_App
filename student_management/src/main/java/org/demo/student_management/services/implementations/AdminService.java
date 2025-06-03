package org.demo.student_management.services.implementations;

import org.demo.student_management.entities.Administrator;
import org.demo.student_management.repositories.AdministratorRepository;
import org.demo.student_management.services.interfaces.AdminServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService implements AdminServiceInt {

    private final AdministratorRepository adminRepository;

    public AdminService(AdministratorRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Administrator createAdministrator(Administrator admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Administrator cannot be null");
        }
        if (adminRepository.existsByUserName(admin.getUserName())) {
            throw new IllegalStateException("Administrator with the same ID already exists");
        }
        return adminRepository.save(admin);
    }

    @Override
    public Administrator updateAdministrator(UUID id, Administrator admin) {
        if (id == null || admin == null) {
            throw new IllegalArgumentException("ID and Administrator cannot be null");
        }
        Optional<Administrator> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            Administrator updatedAdmin = existingAdmin.get();
            updatedAdmin.setFirstName(admin.getFirstName() != null ? admin.getFirstName() : updatedAdmin.getFirstName());
            updatedAdmin.setLastName(admin.getLastName() != null ? admin.getLastName() : updatedAdmin.getLastName());
            updatedAdmin.setUserName(admin.getUserName() != null ? admin.getUserName() : updatedAdmin.getUserName());
            updatedAdmin.setEmail(admin.getEmail() != null ? admin.getEmail() : updatedAdmin.getEmail());
            updatedAdmin.setPassword(admin.getPassword() != null ? admin.getPassword() : updatedAdmin.getPassword());
            return adminRepository.save(updatedAdmin);
        } else {
            throw new IllegalStateException("Administrator with ID " + id + " not found");
        }
    }
    @Override
    public boolean deleteAdministrator(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Administrator with ID " + id + " not found");
        }
    }
    @Override
    public Administrator getAdministratorById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return adminRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Administrator with ID " + id + " not found"));
    }
    @Override
    public List<Administrator> getAllAdministrators() {
        List<Administrator> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            throw new IllegalStateException("No administrators found");
        }
        return admins;
    }
}
