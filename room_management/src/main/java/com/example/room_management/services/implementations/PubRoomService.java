package com.example.room_management.services.implementations;

import com.example.room_management.entities.PubRoom;
import com.example.room_management.repositories.PubRoomRepository;
import com.example.room_management.services.interfaces.PubRoomServiceInt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PubRoomService implements PubRoomServiceInt {

    private PubRoomRepository pubRoomRepository;

    public PubRoomService(PubRoomRepository pubRoomRepository) {
        this.pubRoomRepository = pubRoomRepository;
    }
    @Override
    public PubRoom createPublicRoom(PubRoom pubRoom) {
        if (pubRoom == null) {
            throw new IllegalArgumentException("Public room cannot be null");
        }
        pubRoom.setCreatedAt(Instant.now());
        return pubRoomRepository.save(pubRoom);
    }

    @Override
    public PubRoom updatePublicRoom(UUID id, PubRoom pubRoom) {
        if (id == null || pubRoom == null) {
            throw new IllegalArgumentException("ID and Public room cannot be null");
        }
        Optional<PubRoom> existingRoom = pubRoomRepository.findById(id);
        if (existingRoom.isPresent()) {
            PubRoom updatedRoom = existingRoom.get();
            updatedRoom.setName(pubRoom.getName() != null ? pubRoom.getName() : updatedRoom.getName());
            updatedRoom.setDescription(pubRoom.getDescription() != null ? pubRoom.getDescription() : updatedRoom.getDescription());
            return pubRoomRepository.save(updatedRoom);
        } else {
            throw new IllegalStateException("Public room with ID " + id + " not found");
        }
    }

    @Override
    public boolean deletePublicRoom(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (pubRoomRepository.existsById(id)) {
            pubRoomRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Public room with ID " + id + " not found");
        }
    }

    @Override
    public PubRoom getPublicRoomById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return pubRoomRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Public room with ID " + id + " not found"));
    }

    @Override
    public List<PubRoom> getAllPublicRooms() {
        return pubRoomRepository.findAll();
    }
}
