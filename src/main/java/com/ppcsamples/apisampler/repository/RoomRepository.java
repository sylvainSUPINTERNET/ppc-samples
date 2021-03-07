package com.ppcsamples.apisampler.repository;

import java.util.List;

import com.ppcsamples.apisampler.models.Room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
 
}