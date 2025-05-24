package com.example.room_management.services.implementations;

import com.example.room_management.entities.Participation;
import com.example.room_management.repositories.ParticipationRepository;
import com.example.room_management.services.interfaces.ParticipationServiceInt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipationService implements ParticipationServiceInt {

    private final ParticipationRepository participationRepository;

    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    @Override
    public Participation createParticipation(Participation participation) {
        if (participation == null) {
            throw new IllegalArgumentException("Participation cannot be null");
        }
        
        // Set join time if not already set
        if (participation.getJoinedAt() == null) {
            participation.setJoinedAt(Instant.now());
        }
        
        return participationRepository.save(participation);
    }

    @Override
    public Participation updateParticipation(UUID id, Participation participation) {
        if (id == null || participation == null) {
            throw new IllegalArgumentException("ID and Participation cannot be null");
        }
        
        Optional<Participation> existingParticipation = participationRepository.findById(id);
        if (existingParticipation.isPresent()) {
            Participation updatedParticipation = existingParticipation.get();
            
            // Update fields if they have changed
            if (participation.getId_student() != null) {
                updatedParticipation.setId_student(participation.getId_student());
            }
            
            if (participation.getPubRoom() != null) {
                updatedParticipation.setPubRoom(participation.getPubRoom());
            }
            
            // Update left time if provided
            if (participation.getLeftAt() != null) {
                updatedParticipation.setLeftAt(participation.getLeftAt());
            }
            
            return participationRepository.save(updatedParticipation);
        } else {
            throw new IllegalStateException("Participation with ID " + id + " not found");
        }
    }

    @Override
    public boolean deleteParticipation(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        if (participationRepository.existsById(id)) {
            participationRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Participation with ID " + id + " not found");
        }
    }

    @Override
    public Participation getParticipationById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        return participationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Participation with ID " + id + " not found"));
    }

    @Override
    public List<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    @Override
    public List<Participation> getParticipationsByStudentId(UUID studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        
        return participationRepository.findAll().stream()
                .filter(participation -> studentId.equals(participation.getId_student()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Participation> getParticipationsByRoomId(UUID roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        
        return participationRepository.findAll().stream()
                .filter(participation -> participation.getPubRoom() != null && 
                        roomId.equals(participation.getPubRoom().getId()))
                .collect(Collectors.toList());
    }
}
