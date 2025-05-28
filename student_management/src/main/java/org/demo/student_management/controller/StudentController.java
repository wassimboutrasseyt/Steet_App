package org.demo.student_management.controller;

import org.demo.student_management.entities.Student;
import org.demo.student_management.services.implementations.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
        Optional<Student> student = studentService.getStudentByEmail(email);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{userName}")
    public ResponseEntity<Student> getStudentByUserName(@PathVariable String userName) {
        Student student = studentService.getStudentByUserName(userName);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.saveStudent(student));
    }
    
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        return updatedStudent != null ? 
                ResponseEntity.ok(updatedStudent) : 
                ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")

    public ResponseEntity<Boolean> deleteStudent(@PathVariable UUID id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? 
                ResponseEntity.ok(true) : 
                ResponseEntity.notFound().build();
    }
}
