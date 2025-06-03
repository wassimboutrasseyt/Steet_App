package org.demo.student_management.repositories;

import java.util.UUID;

import org.demo.student_management.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,UUID>{

    boolean existsByUserName(String userName);

    void removeAllByUserName(String userName);
}