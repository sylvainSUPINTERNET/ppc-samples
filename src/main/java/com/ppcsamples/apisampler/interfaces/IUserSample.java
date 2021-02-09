package com.ppcsamples.apisampler.interfaces;

import com.ppcsamples.apisampler.models.UserSampleModel;

public interface IUserSample {
    
    UserSampleModel getUserSampleModel(String email, String name);
}
