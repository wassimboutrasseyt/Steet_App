//package com.example.room_management.controller.restController;
//
//
//import com.example.room_management.entities.PrvRoom;
//import com.example.room_management.services.implementations.ModeratorService;
//import com.example.room_management.services.implementations.PrvRoomService;
//import org.springframework.graphql.data.method.annotation.Argument;
//import org.springframework.graphql.data.method.annotation.MutationMapping;
//import org.springframework.graphql.data.method.annotation.QueryMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("rooms")
//public class ModeratorController {
//
//    private ModeratorService moderatorService;
//    private PrvRoomService prvRoomService;
//    public ModeratorController(ModeratorService moderatorService,PrvRoomService prvRoomService) {
//        this.moderatorService = moderatorService;
//        this.prvRoomService = prvRoomService;
//    }
//
//    @PostMapping
//    public String inviteStudents(@Argument String roomId, @Argument String studentId) {
//        return moderatorService.inviteStudents(roomId, studentId);
//    }
//
//    @PostMapping
//    public String kickMember(@Argument String roomId, @Argument String memberId) {
//        return moderatorService.kickMember(roomId, memberId);
//    }
//
//    @PostMapping
//    public String promoteMember(@Argument String roomId, @Argument String memberId) {
//        return moderatorService.promoteMember(roomId, memberId);
//    }
//
//    @PostMapping
//    public String createPrivateRoom(@Argument String roomName ,@Argument String description) {
//        return "Request has been sent successfully";
//    }
//
//    @PostMapping
//    public PrvRoom updatePrivateRoom(@Argument UUID id, @Argument PrvRoom prvRoom) {
//        return prvRoomService.updatePrivateRoom(id, prvRoom);
//    }
//
//    @PostMapping
//    public boolean deletePrivateRoom(@Argument UUID id) {
//        return prvRoomService.deletePrivateRoom(id);
//    }
//
//    @GetMapping
//    public List<PrvRoom> allPrivateRoomsByUser(@Argument UUID userId) {
//        return prvRoomService.getAllPrivateRooms();
//    }
//
//
//}
