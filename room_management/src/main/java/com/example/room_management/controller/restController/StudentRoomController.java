package com.example.room_management.controller.restController;

import com.example.room_management.dto.StudentRoomsDTO;
import com.example.room_management.entities.PrvRoom;
import com.example.room_management.services.implementations.StudentRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/student/rooms")
public class StudentRoomController {
    
    private final StudentRoomService studentRoomService;
    
    public StudentRoomController(StudentRoomService studentRoomService) {
        this.studentRoomService = studentRoomService;
    }
    
    @PostMapping("/private/create")
    public ResponseEntity<PrvRoom> createPrivateRoom(
            @RequestParam UUID studentId,
            @RequestParam String roomName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String imageUrl,
            @RequestParam(required = false, defaultValue = "false") boolean isVisible) {
        
        PrvRoom newRoom = studentRoomService.createPrivateRoom(
                studentId, roomName, description, imageUrl, isVisible);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
    }
    
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentRoomsDTO> getStudentRoomInfo(@PathVariable UUID studentId) {
        return ResponseEntity.ok(studentRoomService.getStudentRoomInfo(studentId));
    }
    
    @PostMapping("/invite")
    public ResponseEntity<Boolean> inviteStudentToRoom(
            @RequestParam UUID roomId,
            @RequestParam UUID invitedStudentId,
            @RequestParam UUID invitingStudentId) {
        
        return ResponseEntity.ok(studentRoomService.inviteStudentToRoom(
                roomId, invitedStudentId, invitingStudentId));
    }
    
    @PostMapping("/leave")
    public ResponseEntity<Boolean> leavePrivateRoom(
            @RequestParam UUID roomId,
            @RequestParam UUID studentId) {
        
        return ResponseEntity.ok(studentRoomService.leavePrivateRoom(roomId, studentId));
    }
}
