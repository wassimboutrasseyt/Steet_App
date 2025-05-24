package com.example.room_management.services.interfaces;

import com.example.room_management.dto.StudentRoomsDTO;
import com.example.room_management.entities.PrvRoom;
import java.util.UUID;

/**
 * Interface for student-focused room operations
 */
public interface StudentRoomServiceInt {
    
    /**
     * Creates a private room for a student
     * 
     * @param studentId The ID of the student creating the room
     * @param roomName The name of the room
     * @param description Optional description of the room
     * @param imageUrl Optional URL for the room image
     * @param isVisible Whether the room should be visible to others
     * @return The created private room
     */
    PrvRoom createPrivateRoom(UUID studentId, String roomName, String description, 
                              String imageUrl, boolean isVisible);
    
    /**
     * Gets all room information for a student - created rooms, rooms they're a member of, 
     * and pending invitations
     * 
     * @param studentId The ID of the student
     * @return DTO containing all room information for the student
     */
    StudentRoomsDTO getStudentRoomInfo(UUID studentId);
    
    /**
     * Invites another student to a room
     * 
     * @param roomId The ID of the room
     * @param invitedStudentId The ID of the student being invited
     * @param invitingStudentId The ID of the student sending the invitation
     * @return True if invitation was successful
     */
    boolean inviteStudentToRoom(UUID roomId, UUID invitedStudentId, UUID invitingStudentId);
    
    /**
     * Leaves a private room (for members)
     * 
     * @param roomId The ID of the room
     * @param studentId The ID of the student leaving the room
     * @return True if leaving was successful
     */
    boolean leavePrivateRoom(UUID roomId, UUID studentId);
}
