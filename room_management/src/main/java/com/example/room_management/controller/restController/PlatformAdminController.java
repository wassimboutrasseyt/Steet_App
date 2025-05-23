package com.example.room_management.controller.restController;

import com.example.room_management.entities.PubRoom;
import com.example.room_management.services.implementations.PubRoomService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("roomsAdmin")
@RestController
public class PlatformAdminController {
    private final PubRoomService pubRoomService;

    public PlatformAdminController(PubRoomService pubRoomService) {this.pubRoomService = pubRoomService;}

    @PostMapping("/createPublicRoom")
    public PubRoom createPublicRoom(@RequestBody PubRoom pubRoom) {
        return pubRoomService.createPublicRoom(pubRoom);
    }

    @PostMapping("updatePublicRoom")
    public PubRoom updatePublicRoom(@RequestParam UUID id, @RequestBody PubRoom pubRoom) {
        return pubRoomService.updatePublicRoom(id, pubRoom);
    }

    @DeleteMapping("deletePublicRoom")
    public boolean deletePublicRoom(@RequestParam UUID id) {
        return pubRoomService.deletePublicRoom(id);
    }

    @GetMapping("publicRoomById")
    public PubRoom publicRoomById(@RequestParam UUID id) {
        return pubRoomService.getPublicRoomById(id);
    }

    @GetMapping("allPublicRooms")
    public List<PubRoom> allPublicRooms() {
        return pubRoomService.getAllPublicRooms();
    }
}
