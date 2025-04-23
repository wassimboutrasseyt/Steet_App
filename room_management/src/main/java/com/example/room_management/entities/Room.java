package com.example.room_management.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Room {
    @Id @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;
    private String name;
    private String description;
    private Instant createdAt;
//    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
//    private List<Message> messages;
}