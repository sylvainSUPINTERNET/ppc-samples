package com.ppcsamples.apisampler.repository;

import java.util.List;

import com.ppcsamples.apisampler.models.Room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    @Query(value="{'usersUuids' : ?0}")
    List<Room> findAllByUsersUuids(String currentUserUuid);
}