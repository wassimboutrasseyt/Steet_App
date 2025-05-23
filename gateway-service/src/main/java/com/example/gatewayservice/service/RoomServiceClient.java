//package com.example.gatewayservice.service;
//
//
//import com.example.gatewayservice.dto.PubRoom;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.UUID;
//
//@FeignClient(name = "room-management", path = "/rooms")
//public interface RoomServiceClient {
//
//    @PostMapping("/createPublicRoom")
//    PubRoom createPublicRoom(@RequestBody PubRoom pubRoom);
//
//    @PostMapping("/updatePublicRoom")
//    PubRoom updatePublicRoom(@RequestParam UUID id, @RequestBody PubRoom pubRoom);
//
//    @DeleteMapping("/deletePublicRoom")
//    boolean deletePublicRoom(@RequestParam UUID id);
//
//    @GetMapping("/publicRoomById")
//    PubRoom getPublicRoomById(@RequestParam UUID id);
//
//    @GetMapping("/allPublicRooms")
//    Mono<List<PubRoom>> getAllPublicRooms();
//}