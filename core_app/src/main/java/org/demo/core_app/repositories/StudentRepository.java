package org.demo.core_app.repositories;

import org.demo.core_app.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    Student findByUserName(String userName);
    void deleteById(UUID id);
}