package org.demo.student_management.services.implementations;

import org.demo.student_management.dto.ChangePasswordRequest;
import org.demo.student_management.dto.KeycloakTokenResponse;
import org.demo.student_management.dto.RegisterRequest;
import org.demo.student_management.entities.User;
import org.demo.student_management.repositories.UserRepository;
import org.demo.student_management.services.interfaces.UserServiceInt;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;

import java.util.*;

@Service
public class UserService implements UserServiceInt {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Check if user with same email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("User with email " + user.getEmail() + " already exists");
        }

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID id, User user) {
        if (id == null || user == null) {
            throw new IllegalArgumentException("ID and User cannot be null");
        }

        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();

            // Update user fields
            updatedUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : updatedUser.getFirstName());
            updatedUser.setLastName(user.getLastName() != null ? user.getLastName() : updatedUser.getLastName());
            updatedUser.setUserName(user.getUserName() != null ? user.getUserName() : updatedUser.getUserName());

            // If email is being updated, check that it's not already in use
            if (user.getEmail() != null && !user.getEmail().equals(updatedUser.getEmail())) {
                if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                    throw new IllegalStateException("Email " + user.getEmail() + " is already in use");
                }
                updatedUser.setEmail(user.getEmail());
            }

            // Only update password if a new one is provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            return userRepository.save(updatedUser);
        } else {
            throw new IllegalStateException("User with ID " + id + " not found");
        }
    }

    @Override
    public boolean deleteUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("User with ID " + id + " not found");
        }
    }

    @Override
    public User getUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User with ID " + id + " not found"));
    }

    @Override
    public User getUserByUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalStateException("User with username " + userName + " not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User with email " + email + " not found"));
    }

    @Override
    public User login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email and password cannot be null or empty");
        }

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new IllegalStateException("Invalid email or password");
        }
    }

    @Override
    public KeycloakTokenResponse loginWithKeycloak(String username, String password) throws Exception {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", "steet-app-client");
        form.add("client_secret", "9lB2zN15BGqBgfOFLiEtMiW4oZzAHGlf");
        form.add("username", username);
        form.add("password", password);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);

        ResponseEntity<KeycloakTokenResponse> response = restTemplate.exchange(
                "http://localhost:8088/realms/steet-app-realm/protocol/openid-connect/token",
                HttpMethod.POST,
                entity,
                KeycloakTokenResponse.class);

        return response.getBody();
    }

    // Improved Service Method
    @Override
    public void registerUserInKeycloak(RegisterRequest request) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Step 1: Get admin token
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> tokenForm = new LinkedMultiValueMap<>();
            tokenForm.add("grant_type", "password");
            tokenForm.add("client_id", "admin-cli");
            tokenForm.add("username", "admin");
            tokenForm.add("password", "admin");

            HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenForm, tokenHeaders);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                    "http://localhost:8088/realms/master/protocol/openid-connect/token",
                    HttpMethod.POST, tokenEntity, Map.class);

            if (tokenResponse.getStatusCode() != HttpStatus.OK || tokenResponse.getBody() == null) {
                throw new RuntimeException("Failed to get admin token");
            }

            String adminToken = (String) tokenResponse.getBody().get("access_token");

            // Step 2: Create the user
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setContentType(MediaType.APPLICATION_JSON);
            userHeaders.setBearerAuth(adminToken);

            Map<String, Object> userPayload = new HashMap<>();
            userPayload.put("username", request.getUsername());
            userPayload.put("email", request.getEmail());
            userPayload.put("firstName", request.getFirstName());
            userPayload.put("lastName", request.getLastName());
            userPayload.put("enabled", true);
            userPayload.put("emailVerified", true); // Optional: set email as verified

            HttpEntity<Map<String, Object>> userEntity = new HttpEntity<>(userPayload, userHeaders);
            ResponseEntity<Void> userCreateResponse = restTemplate.exchange(
                    "http://localhost:8088/admin/realms/steet-app-realm/users",
                    HttpMethod.POST, userEntity, Void.class);

            if (userCreateResponse.getStatusCode() != HttpStatus.CREATED) {
                throw new RuntimeException("Failed to create user. Status: " + userCreateResponse.getStatusCode());
            }

            // Step 3: Get user ID from Location header (more efficient)
            String locationHeader = userCreateResponse.getHeaders().getFirst("Location");
            String userId;

            if (locationHeader != null && locationHeader.contains("/users/")) {
                userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
            } else {
                // Fallback: search for user
                ResponseEntity<List> usersResponse = restTemplate.exchange(
                        "http://localhost:8088/admin/realms/steet-app-realm/users?username=" + request.getUsername(),
                        HttpMethod.GET, new HttpEntity<>(userHeaders), List.class);

                if (usersResponse.getBody() == null || usersResponse.getBody().isEmpty()) {
                    throw new RuntimeException("User created but could not retrieve user ID");
                }

                userId = ((Map<String, String>) usersResponse.getBody().get(0)).get("id");
            }

            // Step 4: Set password
            Map<String, Object> pwdPayload = new HashMap<>();
            pwdPayload.put("type", "password");
            pwdPayload.put("value", request.getPassword());
            pwdPayload.put("temporary", false);

            HttpEntity<Map<String, Object>> pwdEntity = new HttpEntity<>(pwdPayload, userHeaders);
            ResponseEntity<Void> pwdResponse = restTemplate.exchange(
                    "http://localhost:8088/admin/realms/steet-app-realm/users/" + userId + "/reset-password",
                    HttpMethod.PUT, pwdEntity, Void.class);

            if (pwdResponse.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new RuntimeException("User created but failed to set password");
            }

            System.out.println("User registered successfully in Keycloak with ID: " + userId);

        } catch (Exception e) {
            System.err.println("Keycloak registration failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request) throws Exception {
        // Validate that new password and confirmation match
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirmation do not match");
        }

        // First, verify current password with Keycloak
        try {
            loginWithKeycloak(username, request.getCurrentPassword());
        } catch (Exception e) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Update password in Keycloak
        updatePasswordInKeycloak(username, request.getNewPassword());

        System.out.println("Password changed successfully for user: " + username);
    }

    private void updatePasswordInKeycloak(String username, String newPassword) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Step 1: Get admin token
            HttpHeaders tokenHeaders = new HttpHeaders();
            tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> tokenForm = new LinkedMultiValueMap<>();
            tokenForm.add("grant_type", "password");
            tokenForm.add("client_id", "admin-cli");
            tokenForm.add("username", "admin");
            tokenForm.add("password", "admin");

            HttpEntity<MultiValueMap<String, String>> tokenEntity = new HttpEntity<>(tokenForm, tokenHeaders);
            ResponseEntity<Map> tokenResponse = restTemplate.exchange(
                    "http://localhost:8088/realms/master/protocol/openid-connect/token",
                    HttpMethod.POST, tokenEntity, Map.class);

            if (tokenResponse.getStatusCode() != HttpStatus.OK || tokenResponse.getBody() == null) {
                throw new RuntimeException("Failed to get admin token");
            }

            String adminToken = (String) tokenResponse.getBody().get("access_token");

            // Step 2: Get user ID
            HttpHeaders userHeaders = new HttpHeaders();
            userHeaders.setContentType(MediaType.APPLICATION_JSON);
            userHeaders.setBearerAuth(adminToken);

            ResponseEntity<List> usersResponse = restTemplate.exchange(
                    "http://localhost:8088/admin/realms/steet-app-realm/users?username=" + username,
                    HttpMethod.GET, new HttpEntity<>(userHeaders), List.class);

            if (usersResponse.getBody() == null || usersResponse.getBody().isEmpty()) {
                throw new RuntimeException("User not found in Keycloak");
            }

            String userId = ((Map<String, String>) usersResponse.getBody().get(0)).get("id");

            // Step 3: Update password
            Map<String, Object> pwdPayload = new HashMap<>();
            pwdPayload.put("type", "password");
            pwdPayload.put("value", newPassword);
            pwdPayload.put("temporary", false);

            HttpEntity<Map<String, Object>> pwdEntity = new HttpEntity<>(pwdPayload, userHeaders);
            ResponseEntity<Void> pwdResponse = restTemplate.exchange(
                    "http://localhost:8088/admin/realms/steet-app-realm/users/" + userId + "/reset-password",
                    HttpMethod.PUT, pwdEntity, Void.class);

            if (pwdResponse.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new RuntimeException("Failed to update password in Keycloak");
            }

        } catch (Exception e) {
            System.err.println("Keycloak password update failed: " + e.getMessage());
            throw new RuntimeException("Password update failed: " + e.getMessage(), e);
        }
    }
    // public void registerUserInKeycloak(RegisterRequest request) throws Exception
    // {
    // RestTemplate restTemplate = new RestTemplate();
    //
    // // Step 1: Get admin token
    // HttpHeaders tokenHeaders = new HttpHeaders();
    // tokenHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    // MultiValueMap<String, String> tokenForm = new LinkedMultiValueMap<>();
    // tokenForm.add("grant_type", "password");
    // tokenForm.add("client_id", "admin-cli");
    // tokenForm.add("username", "admin"); // Keycloak admin username
    // tokenForm.add("password", "admin"); // Keycloak admin password
    //
    // HttpEntity<MultiValueMap<String, String>> tokenEntity = new
    // HttpEntity<>(tokenForm, tokenHeaders);
    // ResponseEntity<Map> tokenResponse = restTemplate.exchange(
    // "http://localhost:8080/realms/master/protocol/openid-connect/token",
    // HttpMethod.POST, tokenEntity, Map.class);
    //
    // String adminToken = (String) tokenResponse.getBody().get("access_token");
    //
    // // Step 2: Create the user
    // HttpHeaders userHeaders = new HttpHeaders();
    // userHeaders.setContentType(MediaType.APPLICATION_JSON);
    // userHeaders.setBearerAuth(adminToken);
    //
    // Map<String, Object> userPayload = new HashMap<>();
    // userPayload.put("username", request.getUsername());
    // userPayload.put("email", request.getEmail());
    // userPayload.put("firstName", request.getFirstName());
    // userPayload.put("lastName", request.getLastName());
    // userPayload.put("enabled", true);
    //
    // HttpEntity<Map<String, Object>> userEntity = new HttpEntity<>(userPayload,
    // userHeaders);
    // ResponseEntity<Void> userCreateResponse = restTemplate.exchange(
    // "http://localhost:8080/admin/realms/steet-app-realm/users",
    // HttpMethod.POST, userEntity, Void.class);
    //
    // if (userCreateResponse.getStatusCode() != HttpStatus.CREATED) {
    // throw new RuntimeException("Failed to create user");
    // }
    //
    // // Step 3: Get user ID (you can skip this if the POST response returns it)
    // ResponseEntity<List> usersResponse = restTemplate.exchange(
    // "http://localhost:8080/admin/realms/steet-app-realm/users?username=" +
    // request.getUsername(),
    // HttpMethod.GET, new HttpEntity<>(userHeaders), List.class);
    //
    // String userId = ((Map<String, String>)
    // usersResponse.getBody().get(0)).get("id");
    //
    // // Step 4: Set password
    // Map<String, Object> pwdPayload = new HashMap<>();
    // pwdPayload.put("type", "password");
    // pwdPayload.put("value", request.getPassword());
    // pwdPayload.put("temporary", false);
    //
    // HttpEntity<Map<String, Object>> pwdEntity = new HttpEntity<>(pwdPayload,
    // userHeaders);
    // restTemplate.exchange(
    // "http://localhost:8080/admin/realms/steet-app-realm/users/" + userId +
    // "/reset-password",
    // HttpMethod.PUT, pwdEntity, Void.class);
    // }

}
