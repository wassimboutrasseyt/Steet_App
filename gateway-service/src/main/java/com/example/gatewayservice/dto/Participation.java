package com.example.gatewayservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.UUID;

public class Participation {
    private UUID id;
    private UUID id_student;
    private Instant joinedAt;
    private Instant leftAt;
}
