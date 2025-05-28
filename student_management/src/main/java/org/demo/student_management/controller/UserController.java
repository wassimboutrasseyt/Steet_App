package org.demo.student_management.controller;

import org.demo.student_management.dto.KeycloakTokenResponse;
import org.demo.student_management.dto.RegisterRequest;
import org.demo.student_management.entities.User;
import org.demo.student_management.services.implementations.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
//        try {
//            userService.registerUserInKeycloak(request);
//            return ResponseEntity.ok("User registered successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
//        }
//    }

    // Improved Controller
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        System.out.println("=== REGISTRATION ENDPOINT REACHED ===");
        System.out.println("Username: " + request.getUsername());
        System.out.println("Email: " + request.getEmail());

        // Basic validation
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username is required"));
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Email is required"));
        }

        if (request.getPassword() == null || request.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Password must be at least 4 characters"));
        }

        try {
            userService.registerUserInKeycloak(request);
            System.out.println("=== REGISTRATION SUCCESSFUL ===");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "User registered successfully",
                            "username", request.getUsername(),
                            "email", request.getEmail()
                    ));

        } catch (Exception e) {
            System.err.println("=== REGISTRATION FAILED ===");
            System.err.println("Error: " + e.getMessage());

            // Handle specific Keycloak errors
            String errorMessage = e.getMessage();
            if (errorMessage.contains("409") || errorMessage.contains("Conflict")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "User already exists"));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Registration failed", "details", errorMessage));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        System.out.println("=== LOGIN ENDPOINT REACHED ===");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        try {
            KeycloakTokenResponse tokenResponse = userService.loginWithKeycloak(username, password);
            System.out.println("=== LOGIN SUCCESSFUL ===");
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            System.err.println("=== LOGIN FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
