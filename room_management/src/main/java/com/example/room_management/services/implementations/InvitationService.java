package com.example.room_management.services.implementations;

import com.example.room_management.entities.Invitation;
import com.example.room_management.entities.Membership;
import com.example.room_management.entities.PrvRoom;
import com.example.room_management.entities.enums.Role;
import com.example.room_management.repositories.InvitationRepository;
import com.example.room_management.repositories.MemberShipRepository;
import com.example.room_management.repositories.PrvRoomRepository;
import com.example.room_management.services.interfaces.InvitationServiceInt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvitationService implements InvitationServiceInt {

    private final InvitationRepository invitationRepository;
    private final PrvRoomRepository prvRoomRepository;
    private final MemberShipRepository memberShipRepository;

    public InvitationService(InvitationRepository invitationRepository,
                          PrvRoomRepository prvRoomRepository,
                          MemberShipRepository memberShipRepository) {
        this.invitationRepository = invitationRepository;
        this.prvRoomRepository = prvRoomRepository;
        this.memberShipRepository = memberShipRepository;
    }

    @Override
    public boolean inviteStudentToRoom(UUID roomId, UUID invitedStudentId, UUID inviterId) {
        // Validate input
        if (roomId == null || invitedStudentId == null || inviterId == null) {
            throw new IllegalArgumentException("Room ID, invited student ID, and inviter ID cannot be null");
        }
        
        // Check if the room exists
        Optional<PrvRoom> roomOptional = prvRoomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new IllegalStateException("Private room with ID " + roomId + " not found");
        }
        
        PrvRoom room = roomOptional.get();
        
        // Check if the inviter is the creator or a moderator of the room
        boolean isAuthorized = room.getCreatedBy().equals(inviterId);
        
        if (!isAuthorized) {
            // Check if the inviter is a member with moderator role
            boolean isModerator = room.getMemberships().stream()
                .anyMatch(m -> m.getId_student().equals(inviterId) && 
                          m.getRole().equals(Role.MODERATOR) && 
                          m.isActive());
            
            if (!isModerator) {
                throw new IllegalStateException("User with ID " + inviterId + 
                    " is not authorized to send invitations for this room");
            }
        }
        
        // Check if student is already a member
        boolean isAlreadyMember = room.getMemberships().stream()
            .anyMatch(m -> m.getId_student().equals(invitedStudentId) && m.isActive());
        
        if (isAlreadyMember) {
            throw new IllegalStateException("Student with ID " + invitedStudentId + 
                " is already a member of this room");
        }
        
        // Check if invitation already exists
        Optional<Invitation> existingInvitation = invitationRepository
            .findByRoomIdAndInvitedStudentId(roomId, invitedStudentId);
        
        if (existingInvitation.isPresent() && 
            existingInvitation.get().getStatus() == Invitation.InvitationStatus.PENDING) {
            throw new IllegalStateException("An invitation is already pending for this student");
        }
        
        // Create and save the invitation
        Invitation invitation = new Invitation();
        invitation.setRoom(room);
        invitation.setInvitedStudentId(invitedStudentId);
        invitation.setInviterStudentId(inviterId);
        invitation.setStatus(Invitation.InvitationStatus.PENDING);
        invitation.setCreatedAt(Instant.now());
        
        invitationRepository.save(invitation);
        return true;
    }

    @Override
    public boolean acceptInvitation(UUID roomId, UUID studentId) {
        // Validate input
        if (roomId == null || studentId == null) {
            throw new IllegalArgumentException("Room ID and student ID cannot be null");
        }
        
        // Find the invitation
        Optional<Invitation> invitationOptional = invitationRepository
            .findByRoomIdAndInvitedStudentId(roomId, studentId);
        
        if (invitationOptional.isEmpty() || 
            invitationOptional.get().getStatus() != Invitation.InvitationStatus.PENDING) {
            throw new IllegalStateException("No pending invitation found for this room and student");
        }
        
        Invitation invitation = invitationOptional.get();
        
        // Update invitation status
        invitation.setStatus(Invitation.InvitationStatus.ACCEPTED);
        invitation.setRespondedAt(Instant.now());
        invitationRepository.save(invitation);
        
        // Create membership for the student
        Membership membership = new Membership();
        membership.setId_student(studentId);
        membership.setRoom(invitation.getRoom());
        membership.setRole(Role.MEMBER); // Default role is member
        membership.setActive(true);
        membership.setJoinAt(LocalTime.now());
        
        memberShipRepository.save(membership);
        return true;
    }

    @Override
    public boolean rejectInvitation(UUID roomId, UUID studentId) {
        // Validate input
        if (roomId == null || studentId == null) {
            throw new IllegalArgumentException("Room ID and student ID cannot be null");
        }
        
        // Find the invitation
        Optional<Invitation> invitationOptional = invitationRepository
            .findByRoomIdAndInvitedStudentId(roomId, studentId);
        
        if (invitationOptional.isEmpty() || 
            invitationOptional.get().getStatus() != Invitation.InvitationStatus.PENDING) {
            throw new IllegalStateException("No pending invitation found for this room and student");
        }
        
        Invitation invitation = invitationOptional.get();
        
        // Update invitation status
        invitation.setStatus(Invitation.InvitationStatus.REJECTED);
        invitation.setRespondedAt(Instant.now());
        invitationRepository.save(invitation);
        
        return true;
    }

    @Override
    public List<UUID> getPendingInvitationsForStudent(UUID studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        
        List<Invitation> pendingInvitations = invitationRepository
            .findByInvitedStudentIdAndStatus(studentId, Invitation.InvitationStatus.PENDING);
        
        return pendingInvitations.stream()
            .map(invitation -> invitation.getRoom().getId())
            .collect(Collectors.toList());
    }
}
