package com.example.room_management.controller.restController;

import com.example.room_management.dto.PrvRoomCreateRequest;
import com.example.room_management.entities.PrvRoom;
import com.example.room_management.services.implementations.FileStorageService;
import com.example.room_management.services.implementations.PrvRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/privateRooms")
public class PrvRoomController {

    private final PrvRoomService prvRoomService;
    private final FileStorageService fileStorageService;

    public PrvRoomController(PrvRoomService prvRoomService, FileStorageService fileStorageService) {
        this.prvRoomService = prvRoomService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public ResponseEntity<PrvRoom> createPrivateRoom(@RequestBody PrvRoom prvRoom) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prvRoomService.createPrivateRoom(prvRoom));
    }
    
    @PostMapping("/student/create")
    public ResponseEntity<PrvRoom> createStudentPrivateRoom(@RequestBody PrvRoomCreateRequest request) {
        try {
            String name = request.getPrvRoom().getName();
            String description = request.getPrvRoom().getDescription();
            UUID studentId = request.getPrvRoom().getCreatedBy();
            boolean isVisible = request.getPrvRoom().isVisible();

            if (studentId == null || name == null || name.trim().isEmpty() || request.getImageFile() == null) {
                return ResponseEntity.badRequest().build();
            }

            MultipartFile image = request.getImageFile();

            String imageUrl = fileStorageService.storeFile(image);

            PrvRoom newRoom = new PrvRoom();
            newRoom.setName(name);
            newRoom.setDescription(description);
            newRoom.setImageUrl(imageUrl);
            newRoom.setCreatedBy(studentId);
            newRoom.setVisible(isVisible);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(prvRoomService.createPrivateRoom(newRoom));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PrvRoom> updatePrivateRoom(@PathVariable UUID id, @RequestBody PrvRoom prvRoom) {
        return ResponseEntity.ok(prvRoomService.updatePrivateRoom(id, prvRoom));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePrivateRoom(@PathVariable UUID id) {
        return ResponseEntity.ok(prvRoomService.deletePrivateRoom(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrvRoom> getPrivateRoomById(@PathVariable UUID id) {
        return ResponseEntity.ok(prvRoomService.getPrivateRoomById(id));
    }    @GetMapping("/all")
    public ResponseEntity<List<PrvRoom>> getAllPrivateRooms() {
        return ResponseEntity.ok(prvRoomService.getAllPrivateRooms());
    }
    
    @GetMapping("/visible")
    public ResponseEntity<List<PrvRoom>> getVisiblePrivateRooms() {
        return ResponseEntity.ok(prvRoomService.getAllVisiblePrivateRooms());
    }
    
    @PutMapping("/{id}/visibility")
    public ResponseEntity<PrvRoom> updateRoomVisibility(
            @PathVariable UUID id, 
            @RequestParam boolean isVisible,
            @RequestParam UUID studentId) {
        return ResponseEntity.ok(prvRoomService.updateRoomVisibility(id, isVisible, studentId));
    }
    
    @GetMapping("/creator/{studentId}")
    public ResponseEntity<List<PrvRoom>> getPrivateRoomsByCreator(@PathVariable UUID studentId) {
        return ResponseEntity.ok(prvRoomService.getPrivateRoomsByCreator(studentId));
    }
}
