package com.example.room_management.services.interfaces;

import com.example.room_management.entities.Participation;
import java.util.List;
import java.util.UUID;

public interface ParticipationServiceInt {
    Participation createParticipation(Participation participation);
    Participation updateParticipation(UUID id, Participation participation);
    boolean deleteParticipation(UUID id);
    Participation getParticipationById(UUID id);
    List<Participation> getAllParticipations();
    List<Participation> getParticipationsByStudentId(UUID studentId);
    List<Participation> getParticipationsByRoomId(UUID roomId);
}
