package com.example.room_management.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
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
public class PubRoom extends Room {

    @OneToMany(mappedBy = "pubRoom" ,cascade = CascadeType.REMOVE  )
    private List<Participation> participation;
}