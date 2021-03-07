package com.ppcsamples.apisampler.controller;

import java.util.HashMap;
import java.util.Map;

import com.ppcsamples.apisampler.DTO.UserDetailsDTO;
import com.ppcsamples.apisampler.DTO.room.CreatePayload;
import com.ppcsamples.apisampler.DTO.room.RoomDTO;
import com.ppcsamples.apisampler.models.Room;
import com.ppcsamples.apisampler.services.RoomService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    RoomService roomService;
    
    RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreatePayload createPayload, @RequestAttribute UserDetailsDTO userDetails) {
        RoomDTO newRoom = this.roomService.create(createPayload, userDetails.getUuid());
        Map<String, Object> resp = new HashMap<>();
        resp.put("data", newRoom);
        return ResponseEntity.ok().body(resp);
    }
    
}
