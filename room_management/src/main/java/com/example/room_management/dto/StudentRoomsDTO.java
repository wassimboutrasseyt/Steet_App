package com.example.room_management.dto;

import com.example.room_management.entities.PrvRoom;

import java.util.List;
import java.util.UUID;

/**
 * DTO for returning a student's room information
 */
public class StudentRoomsDTO {
    
    private UUID studentId;
    private List<PrvRoom> createdRooms;
    private List<PrvRoom> memberRooms;
    private List<UUID> pendingInvitations;
    
    public StudentRoomsDTO() {
    }
    
    public StudentRoomsDTO(UUID studentId, List<PrvRoom> createdRooms, 
                           List<PrvRoom> memberRooms, List<UUID> pendingInvitations) {
        this.studentId = studentId;
        this.createdRooms = createdRooms;
        this.memberRooms = memberRooms;
        this.pendingInvitations = pendingInvitations;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public List<PrvRoom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<PrvRoom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<PrvRoom> getMemberRooms() {
        return memberRooms;
    }

    public void setMemberRooms(List<PrvRoom> memberRooms) {
        this.memberRooms = memberRooms;
    }

    public List<UUID> getPendingInvitations() {
        return pendingInvitations;
    }

    public void setPendingInvitations(List<UUID> pendingInvitations) {
        this.pendingInvitations = pendingInvitations;
    }
}
