package com.example.room_management.services.implementations;

import com.example.room_management.services.interfaces.MemberServiceInt;
import org.springframework.stereotype.Service;

@Service
public class MemberService implements MemberServiceInt {

    @Override
    public String shareScreen(String roomId) {
        // Logic for sharing screen
        return "Screen shared";
    }
}
