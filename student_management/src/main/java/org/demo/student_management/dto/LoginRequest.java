package org.demo.student_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
   
    @NotBlank(message = "Username/email is required")
    private String usernameOrEmail;
    
    @NotBlank(message = "Password is required")
    private String password;
}