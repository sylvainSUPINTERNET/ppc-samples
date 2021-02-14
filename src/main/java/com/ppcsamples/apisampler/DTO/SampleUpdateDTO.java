package com.ppcsamples.apisampler.DTO;

import org.springframework.stereotype.Component;

@Component
public class SampleUpdateDTO {
    String albumUuid;
    String sampleUuid;

    SampleUpdateDTO(){}

    SampleUpdateDTO(String albumUuid, String sampleUuid) {
        this.albumUuid = albumUuid;
        this.sampleUuid = sampleUuid;
    }

    public String getAlbumUuid() {
        return this.albumUuid;
    }

    public void setAlbumUuid(String albumUuid) {
        this.albumUuid = albumUuid;
    }

    public String getSampleUuid(){
        return this.sampleUuid;
    }

    public void setSampleUuid(String sampleUuid){
        this.sampleUuid = sampleUuid;
    }


}
