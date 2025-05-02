package com.example.room_management.services.implementations;

import com.example.room_management.entities.PrvRoom;
import com.example.room_management.repositories.PrvRoomRepository;
import com.example.room_management.services.interfaces.PrvRoomServiceInt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrvRoomService implements PrvRoomServiceInt {

    private PrvRoomRepository prvRoomRepository;

    public PrvRoomService(PrvRoomRepository prvRoomRepository) {
        this.prvRoomRepository = prvRoomRepository;
    }
    @Override
    public PrvRoom createPrivateRoom(PrvRoom prvRoom) {
        if (prvRoom == null) {
            throw new IllegalArgumentException("Private room cannot be null");
        }
        return prvRoomRepository.save(prvRoom);
    }

    @Override
    public PrvRoom updatePrivateRoom(UUID id, PrvRoom prvRoom) {
        if (id == null || prvRoom == null) {
            throw new IllegalArgumentException("ID and Private room cannot be null");
        }
        Optional<PrvRoom> existingRoom = prvRoomRepository.findById(id);
        if (existingRoom.isPresent()) {
            PrvRoom updatedRoom = existingRoom.get();
            updatedRoom.setName(prvRoom.getName() != null ? prvRoom.getName() : updatedRoom.getName());
            updatedRoom.setDescription(prvRoom.getDescription() != null ? prvRoom.getDescription() : updatedRoom.getDescription());
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
    }

    @Override
    public List<PrvRoom> getAllPrivateRooms() {
        return prvRoomRepository.findAll();
    }
}
