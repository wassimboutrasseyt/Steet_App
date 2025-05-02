package com.example.room_management.services.interfaces;

public interface ModeratorServiceInt {
    String inviteStudents(String roomId, String studentId);
    String kickMember(String roomId, String memberId);
    String promoteMember(String roomId, String memberId);
}
