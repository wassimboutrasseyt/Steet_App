package com.example.room_management;


import com.example.room_management.entities.Participation;
import com.example.room_management.entities.PubRoom;
import com.example.room_management.repositories.ParticipationRepository;
import com.example.room_management.repositories.PubRoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

@Configuration
public class DataInitializer {


    @Bean
    public CommandLineRunner initData(

            PubRoomRepository pubRoomRepository,
            ParticipationRepository participationRepository) {
                
        return args -> {
            PubRoom pubRoom = new PubRoom();
            pubRoom.setName("General Chat");
            pubRoom.setDescription("A room for everyone to chat and collaborate.");
            pubRoom.setCreatedAt(Instant.now());
            pubRoom.setParticipation(null);  // No participation initially

            pubRoomRepository.save(pubRoom);

            // Add Participation for the students to the pubRoom
            Participation participation1 = new Participation(null,UUID.fromString("cf69c89c-a16e-47af-a563-f35dad77ba3e") , pubRoom, Instant.now(), null);
            Participation participation2 = new Participation(null,UUID.fromString("cf0390d7-2b07-4d4b-9e26-60e3cf379c8b") , pubRoom, Instant.now(), null);
            Participation participation3 = new Participation(null,UUID.fromString("cd4a0566-b232-4079-b9a4-8cf1050893b5") , pubRoom, Instant.now(), null);

            participationRepository.saveAll(List.of(participation1, participation2, participation3));

            System.out.println("Id :"+ pubRoom.getId());
            System.out.println("Name :"+ pubRoom.getName());
            System.out.println("Description :"+ pubRoom.getDescription());
            System.out.println("Created at :"+ pubRoom.getCreatedAt());
            System.out.println("------------------------------------------------------------------");
            System.out.println(participation1);
            System.out.println("------------------------------------------------------------------");
            System.out.println(participation2);
            System.out.println("------------------------------------------------------------------");
            System.out.println(participation3);

        };
    }
}
