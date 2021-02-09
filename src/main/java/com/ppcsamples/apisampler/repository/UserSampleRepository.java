package com.ppcsamples.apisampler.repository;

import com.ppcsamples.apisampler.models.UserSampleModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserSampleRepository extends PagingAndSortingRepository<UserSampleModel, String> {
    UserSampleModel findByEmailAndName(String email, String name);
}