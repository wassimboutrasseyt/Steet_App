package com.example.room_management.repositories;

import com.example.room_management.entities.PrvRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrvRoomRepository extends JpaRepository<PrvRoom, UUID> {
}
