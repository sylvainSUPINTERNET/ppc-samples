package com.ppcsamples.apisampler.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userSample")
public class UserSampleModel {
    @Id 
    public String id;

    @Version
    public Integer version;

    public String sampleUrl;

    public String email;

    public String name;

    public String customFileName;

    public String albumUuid;

    public String sampleUuid;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;

    public UserSampleModel(){};

    public UserSampleModel(String sampleUrl, String email, String name, String customFileName, String albumUuid, String sampleUuid) {
        this.sampleUrl = sampleUrl;
        this.email = email;
        this.name = name;
        this.customFileName = customFileName;
        this.albumUuid = albumUuid;
        this.sampleUuid = sampleUuid;
    }
    
    public void setAlbumUuid(String albumUuid) {
        this.albumUuid = albumUuid;
    }

    public String getSampleUrl(){
        return this.sampleUrl;
    }

    /**
     * @return LocalDateTime return the created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * @return LocalDateTime return the modified
     */
    public LocalDateTime getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

}
