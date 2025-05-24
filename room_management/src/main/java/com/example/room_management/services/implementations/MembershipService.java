package com.example.room_management.services.implementations;

import com.example.room_management.entities.Membership;
import com.example.room_management.repositories.MemberShipRepository;
import com.example.room_management.services.interfaces.MembershipServiceInt;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MembershipService implements MembershipServiceInt {

    private final MemberShipRepository memberShipRepository;

    public MembershipService(MemberShipRepository memberShipRepository) {
        this.memberShipRepository = memberShipRepository;
    }

    @Override
    public Membership createMembership(Membership membership) {
        if (membership == null) {
            throw new IllegalArgumentException("Membership cannot be null");
        }
        
        // Set join time if not already set
        if (membership.getJoinAt() == null) {
            membership.setJoinAt(LocalTime.now());
        }
        
        // Set active status to true by default for new memberships
        membership.setActive(true);
        
        return memberShipRepository.save(membership);
    }

    @Override
    public Membership updateMembership(UUID id, Membership membership) {
        if (id == null || membership == null) {
            throw new IllegalArgumentException("ID and Membership cannot be null");
        }
        
        Optional<Membership> existingMembership = memberShipRepository.findById(id);
        if (existingMembership.isPresent()) {
            Membership updatedMembership = existingMembership.get();
            
            // Update fields if they have changed
            if (membership.getId_student() != null) {
                updatedMembership.setId_student(membership.getId_student());
            }
            
            if (membership.getRoom() != null) {
                updatedMembership.setRoom(membership.getRoom());
            }
            
            if (membership.getRole() != null) {
                updatedMembership.setRole(membership.getRole());
            }
            
            // Only update active status if it has changed
            updatedMembership.setActive(membership.isActive());
            
            // Update leave time if provided
            if (membership.getLeaveAt() != null) {
                updatedMembership.setLeaveAt(membership.getLeaveAt());
            }
            
            return memberShipRepository.save(updatedMembership);
        } else {
            throw new IllegalStateException("Membership with ID " + id + " not found");
        }
    }

    @Override
    public boolean deleteMembership(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        if (memberShipRepository.existsById(id)) {
            memberShipRepository.deleteById(id);
            return true;
        } else {
            throw new IllegalStateException("Membership with ID " + id + " not found");
        }
    }

    @Override
    public Membership getMembershipById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        
        return memberShipRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Membership with ID " + id + " not found"));
    }

    @Override
    public List<Membership> getAllMemberships() {
        return memberShipRepository.findAll();
    }

    @Override
    public List<Membership> getMembershipsByStudentId(UUID studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        
        return memberShipRepository.findAll().stream()
                .filter(membership -> studentId.equals(membership.getId_student()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Membership> getMembershipsByRoomId(UUID roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        
        return memberShipRepository.findAll().stream()
                .filter(membership -> membership.getRoom() != null && 
                        roomId.equals(membership.getRoom().getId()))
                .collect(Collectors.toList());
    }
}
