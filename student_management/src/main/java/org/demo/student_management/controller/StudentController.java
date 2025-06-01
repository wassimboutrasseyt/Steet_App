package org.demo.student_management.controller;

import org.demo.student_management.dto.StudentRegistrationRequest;
import org.demo.student_management.entities.Student;
import org.demo.student_management.services.implementations.StudentService;
import org.demo.student_management.services.implementations.UserService;
import org.demo.student_management.services.implementations.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegistrationRequest request,
            BindingResult bindingResult) {
        System.out.println("=== STUDENT REGISTRATION ENDPOINT REACHED ===");

        // Handle validation errors from the annotations in DTO
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Validation failed", "details", errors));
        }

        try {

                        // 1. Register in Keycloak
            userService.registerUserInKeycloak(request);

            // 2. Create the Student entity
            Student student = Student.builder()
                    .id(UUID.randomUUID()) // Generate a new UUID for the student
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .userName(request.getUsername())
                    .email(request.getEmail())
                    .password(null) // Keycloak handles password, so we set it to null
                    .major(request.getMajor())
                    .dob(request.getDob())
                    .build();


            studentService.saveStudent(student);

            System.out.println("=== STUDENT REGISTRATION SUCCESSFUL ===");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Student registered successfully"));

        } catch (Exception e) {
            System.err.println("=== STUDENT REGISTRATION FAILED ===");
            System.err.println("Error: ------------------------------------------------------------------------------------------------------------------");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Error: ------------------------------------------------------------------------------------------------------------------");

            e.printStackTrace();

            // Handle specific errors
            String errorMessage = e.getMessage();
            if (errorMessage != null &&
                    (errorMessage.contains("409") || errorMessage.contains("Conflict"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Student already exists",
                                "details", errorMessage));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Registration failed", "details", errorMessage));
        }
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
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Boolean> deleteStudent(@PathVariable UUID id) {
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    /**
     * Update a student's profile picture
     * 
     * @param id                The student's ID
     * @param profilePictureUrl The URL to the profile picture
     * @return The updated student with the new profile picture
     */
    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<Student> updateProfilePicture(@PathVariable UUID id, @RequestBody String profilePictureUrl) {
        Student updatedStudent = studentService.updateProfilePicture(id, profilePictureUrl);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    /**
     * Get a student's profile picture
     * 
     * @param id The student's ID
     * @return The URL to the student's profile picture
     */
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<String> getProfilePicture(@PathVariable UUID id) {
        String profilePictureUrl = studentService.getProfilePictureUrl(id);
        return profilePictureUrl != null ? ResponseEntity.ok(profilePictureUrl) : ResponseEntity.notFound().build();
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
