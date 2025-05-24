package com.example.room_management.services.interfaces;

import com.example.room_management.entities.Room;
import java.util.List;
import java.util.UUID;

public interface RoomServiceInt {
    Room createRoom(Room room);
    Room updateRoom(UUID id, Room room);
    boolean deleteRoom(UUID id);
    Room getRoomById(UUID id);
    List<Room> getAllRooms();
}
