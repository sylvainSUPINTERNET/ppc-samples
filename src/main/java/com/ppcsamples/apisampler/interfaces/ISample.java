package com.ppcsamples.apisampler.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface ISample {
    public ResponseEntity<?> uploadSample(MultipartFile file, String authToken);

}
