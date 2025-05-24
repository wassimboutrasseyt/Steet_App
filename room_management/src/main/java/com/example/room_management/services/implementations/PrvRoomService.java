package com.example.room_management.services.implementations;

import com.example.room_management.entities.PrvRoom;
import com.example.room_management.repositories.PrvRoomRepository;
import com.example.room_management.services.interfaces.PrvRoomServiceInt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrvRoomService implements PrvRoomServiceInt {

    private final PrvRoomRepository prvRoomRepository;

    public PrvRoomService(PrvRoomRepository prvRoomRepository) {
        this.prvRoomRepository = prvRoomRepository;
    }    @Override
    public PrvRoom createPrivateRoom(PrvRoom prvRoom) {
        if (prvRoom == null) {
            throw new IllegalArgumentException("Private room cannot be null");
        }
        
        // Validate that createdBy is set - we need to know which student created the room
        if (prvRoom.getCreatedBy() == null) {
            throw new IllegalArgumentException("Creator ID (createdBy) must be specified when creating a private room");
        }
        
        // Set initial properties
        prvRoom.setCreatedAt(java.time.Instant.now());
        prvRoom.setPrivate(true); // Ensure it's set as private
        
        // Set visibility (default to false if not specified)
        if (prvRoom.isVisible()) {
            prvRoom.setVisible(true);
        } else {
            prvRoom.setVisible(false);
        }
        
        return prvRoomRepository.save(prvRoom);
    }@Override
    public PrvRoom updatePrivateRoom(UUID id, PrvRoom prvRoom) {
        if (id == null || prvRoom == null) {
            throw new IllegalArgumentException("ID and Private room cannot be null");
        }
        Optional<PrvRoom> existingRoom = prvRoomRepository.findById(id);
        if (existingRoom.isPresent()) {
            PrvRoom updatedRoom = existingRoom.get();
            updatedRoom.setName(prvRoom.getName() != null ? prvRoom.getName() : updatedRoom.getName());
            updatedRoom.setDescription(prvRoom.getDescription() != null ? prvRoom.getDescription() : updatedRoom.getDescription());
            updatedRoom.setImageUrl(prvRoom.getImageUrl() != null ? prvRoom.getImageUrl() : updatedRoom.getImageUrl());
            
            // Only update visibility if the creator is updating the room
            if (prvRoom.getCreatedBy() != null && prvRoom.getCreatedBy().equals(updatedRoom.getCreatedBy())) {
                updatedRoom.setVisible(prvRoom.isVisible());
            }
            
            return prvRoomRepository.save(updatedRoom);
        } else {
            throw new IllegalStateException("Private room with ID " + id + " not found");
        }
    }

    @Override
    public boolean deletePrivateRoom(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (prvRoomRepository.existsById(id)) {
            prvRoomRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Private room with ID " + id + " not found");
        }
    }

    @Override
    public PrvRoom getPrivateRoomById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return prvRoomRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Private room with ID " + id + " not found"));
    }    @Override
    public List<PrvRoom> getAllPrivateRooms() {
        return prvRoomRepository.findAll();
    }
    
    @Override
    public List<PrvRoom> getAllVisiblePrivateRooms() {
        return prvRoomRepository.findAll().stream()
            .filter(PrvRoom::isVisible)
            .collect(Collectors.toList());
    }
    
    @Override
    public PrvRoom updateRoomVisibility(UUID id, boolean isVisible, UUID requesterId) {
        if (id == null || requesterId == null) {
            throw new IllegalArgumentException("Room ID and requester ID cannot be null");
        }
        
        PrvRoom room = prvRoomRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Private room with ID " + id + " not found"));
        
        // Verify that the requester is the creator of the room
        if (!room.getCreatedBy().equals(requesterId)) {
            throw new IllegalStateException("Only the creator can update room visibility");
        }
        
        room.setVisible(isVisible);
        return prvRoomRepository.save(room);
    }
    
    @Override
    public List<PrvRoom> getPrivateRoomsByCreator(UUID studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        
        return prvRoomRepository.findAll().stream()
            .filter(room -> room.getCreatedBy().equals(studentId))
            .collect(Collectors.toList());
    }
}
