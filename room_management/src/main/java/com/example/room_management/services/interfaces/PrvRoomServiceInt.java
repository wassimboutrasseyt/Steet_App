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
    
    /**
     * Gets all visible private rooms that can be displayed on the home page
     * 
     * @return List of visible private rooms
     */
    List<PrvRoom> getAllVisiblePrivateRooms();
    
    /**
     * Updates the visibility status of a private room
     * 
     * @param id The ID of the private room
     * @param isVisible Whether the room should be visible
     * @param requesterId The ID of the student making the request (must be the creator)
     * @return The updated private room
     */
    PrvRoom updateRoomVisibility(UUID id, boolean isVisible, UUID requesterId);
    
    /**
     * Gets all private rooms created by a specific student
     * 
     * @param studentId The ID of the student
     * @return List of private rooms created by the student
     */
    List<PrvRoom> getPrivateRoomsByCreator(UUID studentId);
}
