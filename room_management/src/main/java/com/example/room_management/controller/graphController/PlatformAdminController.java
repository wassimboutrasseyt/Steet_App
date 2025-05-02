package com.example.room_management.controller.graphController;

import com.example.room_management.entities.PubRoom;
import com.example.room_management.services.implementations.PubRoomService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

//@Controller
@RequestMapping("rooms")
public class PlatformAdminController { //All work perfectly and independently

    private PubRoomService pubRoomService;

    public PlatformAdminController(PubRoomService pubRoomService) {this.pubRoomService = pubRoomService;}

    @MutationMapping
    public PubRoom createPublicRoom(@Argument PubRoom pubRoom) {
        return pubRoomService.createPublicRoom(pubRoom);
    }

    @MutationMapping
    public PubRoom updatePublicRoom(@Argument UUID id, @Argument PubRoom pubRoom) {
        return pubRoomService.updatePublicRoom(id, pubRoom);
    }

    @MutationMapping
    public boolean deletePublicRoom(@Argument UUID id) {
        return pubRoomService.deletePublicRoom(id);
    }

    @QueryMapping
    public PubRoom publicRoomById(@Argument UUID id) {
        return pubRoomService.getPublicRoomById(id);
    }

    @QueryMapping
    public List<PubRoom> allPublicRooms() {
        return pubRoomService.getAllPublicRooms();
    }
}
