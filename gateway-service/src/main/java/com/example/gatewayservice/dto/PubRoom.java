package com.example.gatewayservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class PubRoom {
    private UUID id;
    private String name;
    private String description;
    private Instant createdAt;
    private List<Participation> participation;
}
