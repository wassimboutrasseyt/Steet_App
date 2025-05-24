package com.example.room_management.services.interfaces;

import com.example.room_management.entities.Membership;
import java.util.List;
import java.util.UUID;

public interface MembershipServiceInt {
    Membership createMembership(Membership membership);
    Membership updateMembership(UUID id, Membership membership);
    boolean deleteMembership(UUID id);
    Membership getMembershipById(UUID id);
    List<Membership> getAllMemberships();
    List<Membership> getMembershipsByStudentId(UUID studentId);
    List<Membership> getMembershipsByRoomId(UUID roomId);
}
