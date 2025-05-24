package com.example.room_management.services.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Interface for handling student invitations to private rooms
 */
public interface InvitationServiceInt {
    
    /**
     * Invites a student to a private room
     * 
     * @param roomId The ID of the private room
     * @param invitedStudentId The ID of the student being invited
     * @param inviterId The ID of the student sending the invitation
     * @return True if invitation was successful
     */
    boolean inviteStudentToRoom(UUID roomId, UUID invitedStudentId, UUID inviterId);
    
    /**
     * Accepts an invitation to join a private room
     * 
     * @param roomId The ID of the private room
     * @param studentId The ID of the student accepting the invitation
     * @return True if acceptance was successful
     */
    boolean acceptInvitation(UUID roomId, UUID studentId);
    
    /**
     * Rejects an invitation to join a private room
     * 
     * @param roomId The ID of the private room
     * @param studentId The ID of the student rejecting the invitation
     * @return True if rejection was successful
     */
    boolean rejectInvitation(UUID roomId, UUID studentId);
    
    /**
     * Gets all pending invitations for a student
     * 
     * @param studentId The ID of the student
     * @return List of room IDs with pending invitations
     */
    List<UUID> getPendingInvitationsForStudent(UUID studentId);
}
