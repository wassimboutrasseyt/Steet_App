package org.demo.student_management.repositories;

import org.demo.student_management.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    Student findByUserName(String userName);
    void deleteById(UUID id);
}