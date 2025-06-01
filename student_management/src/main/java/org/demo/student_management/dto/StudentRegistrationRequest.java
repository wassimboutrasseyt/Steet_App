package org.demo.student_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.demo.student_management.entities.enums.Interests;
import org.demo.student_management.entities.enums.Majors;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object for student registration requests.
 * Contains all required fields to create a new student account.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationRequest extends RegisterRequest {
   
   
   
   private Majors major;
   
   @NotNull(message = "Date of birth is required")
   @Past(message = "Date of birth must be in the past")
   private LocalDate dob;
   
   // private List<Interests> interests;
   
   // private String profilePictureUrl;
}