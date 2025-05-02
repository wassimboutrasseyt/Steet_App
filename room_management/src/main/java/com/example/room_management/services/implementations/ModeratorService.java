package com.example.room_management.services.implementations;

import com.example.room_management.services.interfaces.ModeratorServiceInt;
import org.springframework.stereotype.Service;

@Service
public class ModeratorService implements ModeratorServiceInt {
    @Override
    public String inviteStudents(String roomId, String studentId) {
        // Logic for inviting students
        return "Student invited";
    }

    @Override
    public String kickMember(String roomId, String memberId) {
        // Logic for kicking a member
        return "Member kicked";
    }

    @Override
    public String promoteMember(String roomId, String memberId) {
        // Logic for promoting a member
        return "Member promoted";
    }
}
