package com.example.room_management.entities;

import java.time.LocalTime;
import java.util.UUID;

import com.example.room_management.entities.enums.Role;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membership{
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

//    @ManyToOne
//    private Student student;
    private UUID id_student;

    @ManyToOne
    private PrvRoom room; // Properly mapped as a Many-to-One relationship

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive;
    private LocalTime joinAt;
    private LocalTime leaveAt;
}
