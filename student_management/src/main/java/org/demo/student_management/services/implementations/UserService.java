package org.demo.student_management.services.implementations;

import org.demo.student_management.entities.User;
import org.demo.student_management.repositories.UserRepository;
import org.demo.student_management.services.interfaces.UserServiceInt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
