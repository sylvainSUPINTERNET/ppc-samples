package com.ppcsamples.apisampler.controller;

import com.ppcsamples.apisampler.DTO.SampleUpdateDTO;
import com.ppcsamples.apisampler.DTO.UserDetailsDTO;
import com.ppcsamples.apisampler.services.SampleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/samples")
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    SampleService sampleService;

    SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    };


    @GetMapping("/audio/b64")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> getSamplesAudio(@RequestAttribute String filterPpcToken,
     @RequestAttribute UserDetailsDTO userDetails, @RequestParam String albumUuid) {
        return this.sampleService.getSamplesB64ForAlbumUuid(albumUuid);
    }


    @GetMapping("/{fileNamWithExtension}/b64")
    public ResponseEntity<?> getSampleInputStream(@PathVariable String fileNamWithExtension, @RequestAttribute String filterPpcToken, @RequestAttribute UserDetailsDTO userDetails) {
        System.out.println(filterPpcToken);
        System.out.println(userDetails.getEmail());
        return this.sampleService.getSamplesInputStream(fileNamWithExtension);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSample(@RequestPart MultipartFile sample, @RequestPart String customFileName,
            @RequestAttribute String filterPpcToken, @RequestAttribute UserDetailsDTO userDetails) {

        System.out.println(filterPpcToken);
        System.out.println(userDetails.getEmail());

        return this.sampleService.uploadSample(sample, filterPpcToken, userDetails, customFileName);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSampleToAlbum(@RequestBody SampleUpdateDTO sampleUpdateDTO,
            @RequestAttribute String filterPpcToken, @RequestAttribute UserDetailsDTO userDetails) {
        return this.sampleService.addSampleToAlbum(sampleUpdateDTO.getAlbumUuid(), sampleUpdateDTO.getSampleUuid());
    }

}
