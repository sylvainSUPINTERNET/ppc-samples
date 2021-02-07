package com.ppcsamples.apisampler.controller;
import com.ppcsamples.apisampler.services.SampleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SampleController {
    
    Logger logger = LoggerFactory.getLogger(SampleController.class);

    SampleService sampleService;

    SampleController(SampleService sampleService){
        this.sampleService = sampleService;
    };
    

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Ok");
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSample (@RequestPart MultipartFile sample) {
        return this.sampleService.uploadSample(sample);
    }


}
