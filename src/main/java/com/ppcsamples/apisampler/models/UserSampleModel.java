package com.ppcsamples.apisampler.models;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

public class UserSampleModel {
    @Id 
    public String id;

    public String sampleUrl;

    public String email;

    public String name;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;

    UserSampleModel(){};

    UserSampleModel(String sampleUrl, String email, String name) {
        this.sampleUrl = sampleUrl;
        this.email = email;
        this.name = name;
    }
    
}
