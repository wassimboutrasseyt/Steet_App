package org.demo.student_management.controller;

import org.demo.student_management.entities.Student;
import org.demo.student_management.repositories.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/listStudents")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


}
