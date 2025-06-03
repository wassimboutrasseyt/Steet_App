package org.demo.student_management;

import org.demo.student_management.dto.RegisterRequest;
import org.demo.student_management.entities.Administrator;
import org.demo.student_management.entities.Student;
import org.demo.student_management.entities.enums.Interests;
import org.demo.student_management.entities.enums.Majors;
import org.demo.student_management.repositories.AdministratorRepository;
import org.demo.student_management.repositories.StudentRepository;
import org.demo.student_management.services.implementations.AdminService;
import org.demo.student_management.services.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @Bean
    CommandLineRunner init(StudentRepository studentRepository , AdministratorRepository administratorRepository) {
        return args -> {
        //    administratorRepository.deleteAll();
        //    Administrator admin = new Administrator( UUID.randomUUID(),"admin", "Admin", "admin", "admin@admin.com", "admin");
        //    adminService.createAdministrator(admin);
        //    userService.registerUserInKeycloak(new RegisterRequest(admin.getUserName(), admin.getEmail(), admin.getPassword() , admin.getFirstName(), admin.getLastName()));
        };
    }



}
