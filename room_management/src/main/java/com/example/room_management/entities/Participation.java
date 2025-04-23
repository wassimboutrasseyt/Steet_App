package com.example.room_management.entities;


import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
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
public class Participation {

    @Id @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    //    @ManyToOne
//    private Student participant;
    private UUID id_student;

    @ManyToOne
    private PubRoom pubRoom;

    private Instant joinedAt;
    private Instant leftAt;
}
