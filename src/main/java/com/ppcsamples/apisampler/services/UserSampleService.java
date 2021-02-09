package com.ppcsamples.apisampler.services;

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

    @Override
    public UserSampleModel getUserSampleModel(String email, String name) {
        UserSampleModel user = this.userSampleRepository.findByEmailAndName(email, name);
        return user;
    }

    
}
