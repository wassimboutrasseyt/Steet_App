package com.example.room_management.services.interfaces;

import com.example.room_management.entities.PubRoom;

import java.util.List;
import java.util.UUID;

public interface PubRoomServiceInt {
    PubRoom createPublicRoom(PubRoom pubRoom);
    PubRoom updatePublicRoom(UUID id, PubRoom pubRoom);
    boolean deletePublicRoom(UUID id);
    PubRoom getPublicRoomById(UUID id);
    List<PubRoom> getAllPublicRooms();
}
