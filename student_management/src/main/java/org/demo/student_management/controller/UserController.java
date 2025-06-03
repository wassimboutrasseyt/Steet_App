package org.demo.student_management.controller;

import org.demo.student_management.dto.ChangePasswordRequest;
import org.demo.student_management.dto.KeycloakTokenResponse;
import org.demo.student_management.dto.LoginRequest;
import org.demo.student_management.dto.RegisterRequest;
import org.demo.student_management.entities.Administrator;
import org.demo.student_management.entities.User;
import org.demo.student_management.services.implementations.UserService;
import org.demo.student_management.services.implementations.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
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

    // @PostMapping("/register")
    // public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
    // try {
    // userService.registerUserInKeycloak(request);
    // return ResponseEntity.ok("User registered successfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration
    // failed: " + e.getMessage());
    // }
    // }

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
                            "email", request.getEmail()));

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
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        String usernameOrEmail = request.getUsernameOrEmail();
        String password = request.getPassword();
        Boolean isAdmin = false;
        System.out.println("=== LOGIN ENDPOINT REACHED ===");
        System.out.println("Username: " + usernameOrEmail);
        System.out.println("Password: " + password);

        String username = usernameOrEmail;
        boolean isEmail = usernameOrEmail.contains("@");

        if (isEmail) {
            try {
                User user = userService.getUserByEmail(usernameOrEmail);
                isAdmin = user instanceof Administrator;
                if (user == null) {
                    throw new Exception("User not found with email: " + usernameOrEmail);
                }
                username = user.getUserName();
            } catch (Exception e) {
                System.err.println("Error fetching user by email: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        }
        User user = userService.getUserByUserName(username);
        isAdmin = user instanceof Administrator;
        String userId = user.getId().toString();
        System.out.println("userId: " + userId);

        try {
            KeycloakTokenResponse tokenResponse = userService.loginWithKeycloak(username, password);
            System.out.println("=== LOGIN SUCCESSFUL ===");

            return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "accessToken", tokenResponse.getAccess_token(),
                    "isAdmin", isAdmin,
                    "tokenType", tokenResponse.getToken_type()));
        } catch (Exception e) {
            System.err.println("=== LOGIN FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/{username}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable String username,
            @Valid @RequestBody ChangePasswordRequest request,
            BindingResult bindingResult) {

        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Validation failed", "details", errors));
        }

        try {
            userService.changePassword(username, request);

            return ResponseEntity.ok(Map.of(
                    "message", "Password changed successfully",
                    "username", username));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid request", "details", e.getMessage()));

        } catch (Exception e) {
            System.err.println("Password change failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Password change failed", "details", e.getMessage()));
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
