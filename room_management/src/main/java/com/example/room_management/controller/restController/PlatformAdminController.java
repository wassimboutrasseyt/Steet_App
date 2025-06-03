package com.example.room_management.controller.restController;

import com.example.room_management.entities.PubRoom;
import com.example.room_management.services.implementations.PubRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roomsAdmin")
public class PlatformAdminController {
    private final PubRoomService pubRoomService;

    public PlatformAdminController(PubRoomService pubRoomService) {
        this.pubRoomService = pubRoomService;
    }

    @PostMapping("/createPublicRoom")
    public ResponseEntity<PubRoom> createPublicRoom(@RequestBody PubRoom pubRoom) {
        PubRoom created = pubRoomService.createPublicRoom(pubRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/updatePublicRoom")
    public ResponseEntity<PubRoom> updatePublicRoom(@RequestParam UUID id, @RequestBody PubRoom pubRoom) {
        PubRoom updated = pubRoomService.updatePublicRoom(id, pubRoom);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deletePublicRoom")
    public ResponseEntity<?> deletePublicRoom(@RequestParam UUID id) {
        boolean deleted = pubRoomService.deletePublicRoom(id);
        if (deleted) {
            return ResponseEntity.ok().body("Public room deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Public room not found.");
        }
    }

    @GetMapping("/publicRoomById")
    public ResponseEntity<PubRoom> publicRoomById(@RequestParam UUID id) {
        PubRoom room = pubRoomService.getPublicRoomById(id);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(room);
    }

    @GetMapping("/allPublicRooms")
    public ResponseEntity<List<PubRoom>> allPublicRooms() {
        List<PubRoom> rooms = pubRoomService.getAllPublicRooms();
        return ResponseEntity.ok(rooms);
    }
}