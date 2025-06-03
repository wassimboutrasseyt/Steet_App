package com.example.room_management.controller.restController;

import com.example.room_management.entities.Invitation;
import com.example.room_management.services.implementations.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invitations")
public class InvitationController {
    
    private final InvitationService invitationService;
    
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }
    
    @PostMapping("/send")
    public ResponseEntity<Boolean> inviteStudentToRoom(
            @RequestParam UUID roomId,
            @RequestParam UUID invitedStudentId,
            @RequestParam UUID inviterId) {
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(invitationService.inviteStudentToRoom(roomId, invitedStudentId, inviterId));
    }
    
    @PostMapping("/accept")
    public ResponseEntity<Boolean> acceptInvitation(
            @RequestParam UUID roomId,
            @RequestParam UUID studentId) {
        
        return ResponseEntity.ok(invitationService.acceptInvitation(roomId, studentId));
    }
    
    @PostMapping("/reject")
    public ResponseEntity<Boolean> rejectInvitation(
            @RequestParam UUID roomId,
            @RequestParam UUID studentId) {
        
        return ResponseEntity.ok(invitationService.rejectInvitation(roomId, studentId));
    }
    
    @GetMapping("/pending/{studentId}")
    public ResponseEntity<List<Invitation>> getPendingInvitations(@PathVariable UUID studentId) {
        return ResponseEntity.ok(invitationService.getPendingInvitationsForStudent(studentId));
    }
}
