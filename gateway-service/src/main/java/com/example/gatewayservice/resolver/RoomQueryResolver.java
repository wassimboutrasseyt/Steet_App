//package com.example.gatewayservice.resolver;
//
//import com.example.gatewayservice.dto.PubRoom;
//import com.example.gatewayservice.service.RoomServiceClient;
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class RoomQueryResolver implements GraphQLQueryResolver {
//    Logger log = LoggerFactory.getLogger(RoomQueryResolver.class);
//    private RoomServiceClient roomServiceClient;
//    public RoomQueryResolver(RoomServiceClient roomServiceClient) {this.roomServiceClient = roomServiceClient;}
////    public List<PubRoom> getAllPublicRooms() {
////        System.out.println("========================================================================================================================================================================================================================================================================================================");
////        try {
////            return roomServiceClient.getAllPublicRooms();
////        } catch (Exception e) {
////            log.error("Failed to fetch public rooms", e);
////            return List.of();
////        }
////    }
//
//}
