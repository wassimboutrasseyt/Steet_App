package com.example.room_management.entities;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;
    
    @ManyToOne
    private PrvRoom room;
    
    private UUID invitedStudentId; // ID of the student being invited
    
    private UUID inviterStudentId; // ID of the student who sent the invitation
    
    private Instant createdAt;
    
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
    
    private Instant respondedAt; // When the invitation was accepted/rejected
    
    public enum InvitationStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
