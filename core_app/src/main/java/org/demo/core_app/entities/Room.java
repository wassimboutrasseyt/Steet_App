package org.demo.core_app.entities;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
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