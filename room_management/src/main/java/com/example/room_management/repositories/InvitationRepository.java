package com.example.room_management.repositories;

import com.example.room_management.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UUID> {
    
    List<Invitation> findByInvitedStudentIdAndStatus(UUID studentId, Invitation.InvitationStatus status);
    
    List<Invitation> findByInviterStudentId(UUID inviterId);
    
    List<Invitation> findByRoomId(UUID roomId);
    
    Optional<Invitation> findByRoomIdAndInvitedStudentId(UUID roomId, UUID studentId);
}
