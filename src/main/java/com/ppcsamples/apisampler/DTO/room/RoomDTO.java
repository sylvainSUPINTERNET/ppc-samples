package com.ppcsamples.apisampler.DTO.room;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RoomDTO {
    public String uuid;
    public String owner;
    public String displayName;
    public ArrayList<String> usersUuids;
    private LocalDateTime created;
    private LocalDateTime modified;


    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
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
