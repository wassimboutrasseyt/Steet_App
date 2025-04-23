package org.demo.student_management.repositories;

import java.util.UUID;

import org.demo.student_management.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdministratorRepository extends JpaRepository<Administrator,UUID>{

}