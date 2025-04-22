package org.demo.core_app.repositories;

import java.util.UUID;

import org.demo.core_app.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdministratorRepository extends JpaRepository<Administrator,UUID>{

}