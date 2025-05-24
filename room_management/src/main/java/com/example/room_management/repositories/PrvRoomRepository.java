package com.example.room_management.repositories;

import com.example.room_management.entities.PrvRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrvRoomRepository extends JpaRepository<PrvRoom, UUID> {
    /**
     * Find all visible private rooms
     * @return List of visible private rooms
     */
    List<PrvRoom> findByIsVisibleTrue();
    
    /**
     * Find all private rooms created by a specific student
     * @param createdBy The ID of the creator
     * @return List of private rooms created by the student
     */
    List<PrvRoom> findByCreatedBy(UUID createdBy);
}
