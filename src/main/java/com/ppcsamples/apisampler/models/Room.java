package com.ppcsamples.apisampler.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "room")
public class Room {
    @Id 
    public String id;

    public String uuid;

    @Version
    public Integer version;

    public String owner;

    public String displayName;

    public ArrayList<String> usersUuids;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime modified;

    public Room(String displayName, ArrayList<String> usersUuids, String owner, String uuid) {
        this.displayName = displayName;
        this.usersUuids = usersUuids;
        this.owner = owner;
        this.uuid = uuid;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return this.displayName;
    }
    public void setDisplayName(String displayName) {
         this.displayName = displayName;
    }

    public ArrayList<String> getUsersUuids() {
        return this.usersUuids;
    }
    public void setUsersUuids(ArrayList<String> usersUuids) {
        this.usersUuids = usersUuids;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner(){
        return this.owner;
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
