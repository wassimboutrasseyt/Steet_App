package org.demo.student_management.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Table(name = "\"user\"") // Escape the table name
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    // @GeneratedValue(generator = "UUID")
    // @UuidGenerator
    private UUID id;

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    
    // Profile picture URL
    @Column(length = 1000)
    private String profilePictureUrl;
}
