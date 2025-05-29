package org.demo.student_management.controller;

import org.demo.student_management.entities.Student;
import org.demo.student_management.services.implementations.StudentService;
import org.demo.student_management.services.implementations.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final FileStorageService fileStorageService;

    public StudentController(StudentService studentService, FileStorageService fileStorageService) {
        this.studentService = studentService;
        this.fileStorageService = fileStorageService;
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
    
    /**
     * Update a student's profile picture
     * @param id The student's ID
     * @param profilePictureUrl The URL to the profile picture
     * @return The updated student with the new profile picture
     */
    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<Student> updateProfilePicture(@PathVariable UUID id, @RequestBody String profilePictureUrl) {
        Student updatedStudent = studentService.updateProfilePicture(id, profilePictureUrl);
        return updatedStudent != null ? 
                ResponseEntity.ok(updatedStudent) : 
                ResponseEntity.notFound().build();
    }
    
    /**
     * Get a student's profile picture
     * @param id The student's ID
     * @return The URL to the student's profile picture
     */
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<String> getProfilePicture(@PathVariable UUID id) {
        String profilePictureUrl = studentService.getProfilePictureUrl(id);
        return profilePictureUrl != null ? 
                ResponseEntity.ok(profilePictureUrl) : 
                ResponseEntity.notFound().build();
    }

    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("studentId") UUID studentId,
                                                    @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileUrl = "/api/students/profile-image/" + fileName;
        studentService.updateProfilePicture(studentId, fileUrl);
        return ResponseEntity.ok(fileUrl);
    }

    @GetMapping("/profile-image/{fileName:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String fileName) {
        try {
            Path filePath = fileStorageService.getFilePath(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
