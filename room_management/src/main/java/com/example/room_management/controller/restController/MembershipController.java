package com.example.room_management.controller.restController;

import com.example.room_management.entities.Membership;
import com.example.room_management.services.implementations.MembershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/create")
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membershipService.createMembership(membership));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable UUID id, @RequestBody Membership membership) {
        return ResponseEntity.ok(membershipService.updateMembership(id, membership));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMembership(@PathVariable UUID id) {
        return ResponseEntity.ok(membershipService.deleteMembership(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable UUID id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Membership>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Membership>> getMembershipsByStudentId(@PathVariable UUID studentId) {
        return ResponseEntity.ok(membershipService.getMembershipsByStudentId(studentId));
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Membership>> getMembershipsByRoomId(@PathVariable UUID roomId) {
        return ResponseEntity.ok(membershipService.getMembershipsByRoomId(roomId));
    }
}
