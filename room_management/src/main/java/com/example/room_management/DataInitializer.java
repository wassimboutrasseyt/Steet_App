// package com.example.room_management;

// import com.example.room_management.entities.Participation;
// import com.example.room_management.entities.PubRoom;
// import com.example.room_management.repositories.ParticipationRepository;
// import com.example.room_management.repositories.PubRoomRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.time.Instant;
// import java.util.List;
// import java.util.UUID;

// @Configuration
// public class DataInitializer {

//     @Bean
//     public CommandLineRunner initData(
//             PubRoomRepository pubRoomRepository,
//             ParticipationRepository participationRepository) {

//         return args -> {
//             // Room 1
//             PubRoom pubRoom1 = new PubRoom();
//             pubRoom1.setName("General Chat");
//             pubRoom1.setDescription("A room for everyone to chat and collaborate.");
//             pubRoom1.setImageUrl("https://www.amranihassan.site/steet-brand.png");
//             pubRoom1.setCreatedAt(Instant.now());
//             pubRoom1.setParticipation(null);

//             // Room 2
//             PubRoom pubRoom2 = new PubRoom();
//             pubRoom2.setName("Study Group");
//             pubRoom2.setDescription("A room for study discussions.");
//             pubRoom2.setImageUrl("https://www.amranihassan.site/san-amrani.png");
//             pubRoom2.setCreatedAt(Instant.now());
//             pubRoom2.setParticipation(null);

//             // Room 3
//             PubRoom pubRoom3 = new PubRoom();
//             pubRoom3.setName("Project Team");
//             pubRoom3.setDescription("A room for project collaboration.");
//             pubRoom3.setImageUrl("https://www.amranihassan.site/doit-brand.png");
//             pubRoom3.setCreatedAt(Instant.now());
//             pubRoom3.setParticipation(null);

//             pubRoomRepository.saveAll(List.of(pubRoom1, pubRoom2, pubRoom3));

//             // Participations for Room 1
//             Participation participation1 = new Participation(null, UUID.fromString("cf69c89c-a16e-47af-a563-f35dad77ba3e"), pubRoom1, Instant.now(), null);
//             Participation participation2 = new Participation(null, UUID.fromString("cf0390d7-2b07-4d4b-9e26-60e3cf379c8b"), pubRoom1, Instant.now(), null);

//             // Participations for Room 2
//             Participation participation3 = new Participation(null, UUID.fromString("cd4a0566-b232-4079-b9a4-8cf1050893b5"), pubRoom2, Instant.now(), null);

//             // Participations for Room 3
//             Participation participation4 = new Participation(null, UUID.fromString("cf69c89c-a16e-47af-a563-f35dad77ba3e"), pubRoom3, Instant.now(), null);

//             participationRepository.saveAll(List.of(participation1, participation2, participation3, participation4));

//             System.out.println("Rooms created:");
//             for (PubRoom room : List.of(pubRoom1, pubRoom2, pubRoom3)) {
//                 System.out.println("Id :" + room.getId());
//                 System.out.println("Name :" + room.getName());
//                 System.out.println("Description :" + room.getDescription());
//                 System.out.println("Image URL :" + room.getImageUrl());
//                 System.out.println("Created at :" + room.getCreatedAt());
//                 System.out.println("------------------------------------------------------------------");
//             }

//             System.out.println("Participations:");
//             for (Participation p : List.of(participation1, participation2, participation3, participation4)) {
//                 System.out.println(p);
//                 System.out.println("------------------------------------------------------------------");
//             }
//         };
//     }
// }
