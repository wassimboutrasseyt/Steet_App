package com.example.room_management.entities;


import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PubRoom pubRoom;

    private Instant joinedAt;
    private Instant leftAt;
}
