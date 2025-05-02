package com.example.room_management.services.interfaces;

import com.example.room_management.entities.PrvRoom;

import java.util.List;
import java.util.UUID;

public interface PrvRoomServiceInt {
    PrvRoom createPrivateRoom(PrvRoom prvRoom);
    PrvRoom updatePrivateRoom(UUID id, PrvRoom prvRoom);
    boolean deletePrivateRoom(UUID id);
    PrvRoom getPrivateRoomById(UUID id);
    List<PrvRoom> getAllPrivateRooms();
}
