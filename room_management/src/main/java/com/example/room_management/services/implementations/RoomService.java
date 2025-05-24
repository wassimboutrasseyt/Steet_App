package com.example.room_management.services.implementations;

import com.example.room_management.entities.Room;
import com.example.room_management.repositories.RoomRepository;
import com.example.room_management.services.interfaces.RoomServiceInt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService implements RoomServiceInt {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        
        // Set creation time if not set
        if (room.getCreatedAt() == null) {
            room.setCreatedAt(Instant.now());
        }
        
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(UUID id, Room room) {
        if (id == null || room == null) {
            throw new IllegalArgumentException("ID and Room cannot be null");
        }
        
        Optional<Room> existingRoom = roomRepository.findById(id);
        if (existingRoom.isPresent()) {
            Room updatedRoom = existingRoom.get();
            
            // Update fields if they have changed
            updatedRoom.setName(room.getName() != null ? room.getName() : updatedRoom.getName());
            updatedRoom.setDescription(room.getDescription() != null ? room.getDescription() : updatedRoom.getDescription());
            updatedRoom.setImageUrl(room.getImageUrl() != null ? room.getImageUrl() : updatedRoom.getImageUrl());
            
            return roomRepository.save(updatedRoom);
        } else {
            throw new IllegalStateException("Room with ID " + id + " not found");
        }
    }

    @Override
    public boolean deleteRoom(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Room with ID " + id + " not found");
        }
    }

    @Override
    public Room getRoomById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Room with ID " + id + " not found"));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
