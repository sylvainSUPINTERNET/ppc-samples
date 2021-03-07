package com.ppcsamples.apisampler.DTO.room;

public class CreatePayload {
    private String displayName;
    private String targetUserUuid;
    
    public CreatePayload (String displayName, String targetUserUuid) {
        this.displayName = displayName;
        this.targetUserUuid = targetUserUuid;
    }
    

    public String getDisplayName() {
        return this.displayName;
    }

    public String getTargetUserUuid() {
        return this.targetUserUuid;
    }

}
