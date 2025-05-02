package com.example.room_management.controller.graphController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rooms")
public class RoomController {
    @GetMapping("/getRoom")
    public String getRoom() {
        return "Hello World";
    }
}
