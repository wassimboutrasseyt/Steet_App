package com.example.room_management.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrvRoom extends Room {
    private boolean isPrivate;

    private UUID createdBy;

    @OneToMany(mappedBy = "room")
    private List<Membership> memberships;
}