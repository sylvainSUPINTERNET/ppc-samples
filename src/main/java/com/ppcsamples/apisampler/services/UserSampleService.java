package com.ppcsamples.apisampler.services;

import java.util.List;

import com.ppcsamples.apisampler.interfaces.IUserSample;
import com.ppcsamples.apisampler.models.UserSampleModel;
import com.ppcsamples.apisampler.repository.UserSampleRepository;

import org.springframework.stereotype.Service;

@Service
public class UserSampleService implements IUserSample {

    UserSampleRepository userSampleRepository;

    UserSampleService(UserSampleRepository userSampleRepository) {
        this.userSampleRepository = userSampleRepository;
    }

    UserSampleModel createUserSample(UserSampleModel userSampleModel){
        return this.userSampleRepository.save(userSampleModel);
    }

    List<UserSampleModel> createAllUsersSamples(List<UserSampleModel> userSampleModel){
        return this.userSampleRepository.saveAll(userSampleModel);
    }

    
}
