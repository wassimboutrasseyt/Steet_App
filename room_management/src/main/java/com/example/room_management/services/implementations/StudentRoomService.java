package com.example.room_management.services.implementations;

import com.example.room_management.dto.StudentRoomsDTO;
import com.example.room_management.entities.Invitation;
import com.example.room_management.entities.Membership;
import com.example.room_management.entities.PrvRoom;
import com.example.room_management.entities.enums.Role;
import com.example.room_management.repositories.MemberShipRepository;
import com.example.room_management.repositories.PrvRoomRepository;
import com.example.room_management.services.interfaces.InvitationServiceInt;
import com.example.room_management.services.interfaces.StudentRoomServiceInt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentRoomService implements StudentRoomServiceInt {

    private final PrvRoomRepository prvRoomRepository;
    private final MemberShipRepository memberShipRepository;
    private final InvitationServiceInt invitationService;

    public StudentRoomService(PrvRoomRepository prvRoomRepository,
                              MemberShipRepository memberShipRepository,
                              InvitationServiceInt invitationService) {
        this.prvRoomRepository = prvRoomRepository;
        this.memberShipRepository = memberShipRepository;
        this.invitationService = invitationService;
    }

    @Override
    public PrvRoom createPrivateRoom(UUID studentId, String roomName, String description,
                                    String imageUrl, boolean isVisible) {
        if (studentId == null || roomName == null || roomName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID and room name are required");
        }

        PrvRoom newRoom = new PrvRoom();
        newRoom.setName(roomName);
        newRoom.setDescription(description);
        newRoom.setImageUrl(imageUrl);
        newRoom.setCreatedBy(studentId);
        newRoom.setVisible(isVisible);
        newRoom.setCreatedAt(Instant.now());
        newRoom.setPrivate(true);
        
        // Save the room first
        PrvRoom savedRoom = prvRoomRepository.save(newRoom);
        
        // Create membership for the creator as a moderator
        Membership creatorMembership = new Membership();
        creatorMembership.setId_student(studentId);
        creatorMembership.setRoom(savedRoom);
        creatorMembership.setRole(Role.MODERATOR);
        creatorMembership.setActive(true);
        creatorMembership.setJoinAt(LocalTime.now());
        
        memberShipRepository.save(creatorMembership);
        
        return savedRoom;
    }

    @Override
    public StudentRoomsDTO getStudentRoomInfo(UUID studentId) {
        // if (studentId == null) {
        //     throw new IllegalArgumentException("Student ID cannot be null");
        // }
        
        // // Get rooms created by this student
        // List<PrvRoom> createdRooms = prvRoomRepository.findByCreatedBy(studentId);
        
        // // Get all memberships for this student
        // List<Membership> memberships = memberShipRepository.findAll().stream()
        //         .filter(m -> m.getId_student().equals(studentId) && m.isActive())
        //         .collect(Collectors.toList());
        
        // // Extract rooms from memberships (excluding rooms the student created)
        // List<PrvRoom> memberRooms = memberships.stream()
        //         .map(Membership::getRoom)
        //         .filter(room -> !room.getCreatedBy().equals(studentId))
        //         .collect(Collectors.toList());
        
        // // Get pending invitations
        // List<Invitation> pendingInvitations = invitationService.getPendingInvitationsForStudent(studentId);
        
        // return new StudentRoomsDTO(studentId, createdRooms, memberRooms, pendingInvitations);

        return null;
    }

    @Override
    public boolean inviteStudentToRoom(UUID roomId, UUID invitedStudentId, UUID invitingStudentId) {
        return invitationService.inviteStudentToRoom(roomId, invitedStudentId, invitingStudentId);
    }

    @Override
    public boolean leavePrivateRoom(UUID roomId, UUID studentId) {
        if (roomId == null || studentId == null) {
            throw new IllegalArgumentException("Room ID and student ID cannot be null");
        }
        
        // Find the room
        Optional<PrvRoom> roomOptional = prvRoomRepository.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new IllegalStateException("Private room with ID " + roomId + " not found");
        }
        
        PrvRoom room = roomOptional.get();
        
        // Check if student is the creator - creators cannot leave their own rooms
        if (room.getCreatedBy().equals(studentId)) {
            throw new IllegalStateException("Room creator cannot leave their own room. Please delete the room instead.");
        }
        
        // Find the membership
        Optional<Membership> membershipOptional = room.getMemberships().stream()
                .filter(m -> m.getId_student().equals(studentId) && m.isActive())
                .findFirst();
        
        if (membershipOptional.isEmpty()) {
            throw new IllegalStateException("Student is not a member of this room");
        }
        
        Membership membership = membershipOptional.get();
        
        // Mark as inactive and set leave time
        membership.setActive(false);
        membership.setLeaveAt(LocalTime.now());
        
        memberShipRepository.save(membership);
        return true;
    }
}
