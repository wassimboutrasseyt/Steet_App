package com.example.room_management.dto;


import org.springframework.web.multipart.MultipartFile;

import com.example.room_management.entities.PrvRoom;

import lombok.Data;

@Data
public class PrvRoomCreateRequest {
   PrvRoom prvRoom;
   MultipartFile imageFile;
}
