package com.ppcsamples.apisampler.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ppcsamples.apisampler.DTO.room.CreatePayload;
import com.ppcsamples.apisampler.DTO.room.RoomDTO;
import com.ppcsamples.apisampler.models.Room;
import com.ppcsamples.apisampler.repository.RoomRepository;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    RoomRepository roomRepository;

    Logger logger = LoggerFactory.getLogger(RoomService.class);

    ModelMapper modelMapper = new ModelMapper();
    
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public List<RoomDTO> getUserRooms(String currentUserUuid) {
        List<Room> userRooms = this.roomRepository.findAllByUsersUuids(currentUserUuid);
        return userRooms.stream().map(room -> this.modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
    }

    public RoomDTO create(CreatePayload createPayload, String currentUserUuid) {
        this.logger.info("User : {} ", currentUserUuid);
        this.logger.info("asks for user : {} ", createPayload.getTargetUserUuid());
        this.logger.info("to join room : {} ", createPayload.getDisplayName());
        try {
            CompletableFuture<Room> cf = generateRoom(createPayload, currentUserUuid);
            Room newRoomCreated = cf.join();
            RoomDTO roomDto = this.modelMapper.map(newRoomCreated, RoomDTO.class);
            this.logger.info("Room created with success {} - uuid : {}", roomDto.getDisplayName(), roomDto.getUuid());
            return roomDto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            return null;
        }

    }

    public CompletableFuture<Room> generateRoom(CreatePayload createPayload, String currentUserUuid) {
        ArrayList<String> usersUuids = new ArrayList<String>();
        usersUuids.add(createPayload.getTargetUserUuid());
        usersUuids.add(currentUserUuid);
      
        String uuid = UUID.randomUUID().toString();

        Room newRoom = new Room(createPayload.getDisplayName(), usersUuids, currentUserUuid, uuid);
        
        CompletableFuture<Room> cf = CompletableFuture.supplyAsync(() -> this.roomRepository.save(newRoom));

        return cf;
    }
}
