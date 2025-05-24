package com.example.room_management.controller.restController;

import com.example.room_management.entities.Participation;
import com.example.room_management.services.implementations.ParticipationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Participation> createParticipation(@RequestBody Participation participation) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(participationService.createParticipation(participation));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Participation> updateParticipation(@PathVariable UUID id, @RequestBody Participation participation) {
        return ResponseEntity.ok(participationService.updateParticipation(id, participation));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteParticipation(@PathVariable UUID id) {
        return ResponseEntity.ok(participationService.deleteParticipation(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participation> getParticipationById(@PathVariable UUID id) {
        return ResponseEntity.ok(participationService.getParticipationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Participation>> getAllParticipations() {
        return ResponseEntity.ok(participationService.getAllParticipations());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Participation>> getParticipationsByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(participationService.getParticipationsByStudentId(studentId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Participation>> getParticipationsByRoomId(@PathVariable UUID roomId) {
        return ResponseEntity.ok(participationService.getParticipationsByRoomId(roomId));
    }
}
