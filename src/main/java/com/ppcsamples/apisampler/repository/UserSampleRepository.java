package com.ppcsamples.apisampler.repository;

import com.ppcsamples.apisampler.models.UserSampleModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSampleRepository extends MongoRepository<UserSampleModel, String> {
    UserSampleModel findBySampleUuid(String sampleUuid);
}