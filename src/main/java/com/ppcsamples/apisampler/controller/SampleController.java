package com.ppcsamples.apisampler.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ppcsamples.apisampler.metadata.SampleFormatEnum;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SampleController {
    
    Logger logger = LoggerFactory.getLogger(SampleController.class);

    SampleController(){};
    

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("Ok");
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addSample (@RequestPart MultipartFile sample) {
        try {
            
            String[] fileNameParts = sample.getOriginalFilename().split("\\.");


            if ( fileNameParts.length <= 0) {
                return ResponseEntity.badRequest().body("ERROR => invalid file ?");
            }


            String sampleExtension = fileNameParts[1];

            SampleFormatEnum[] formats = SampleFormatEnum.values();
            Stream<SampleFormatEnum> streamSampleFormatEnum = Arrays.stream(formats);
            //st.forEach(e -> System.out.println(e));
            List<SampleFormatEnum> formatFound = streamSampleFormatEnum.filter(e -> e.toString().toLowerCase().contentEquals(sampleExtension))
                    .collect(Collectors.toList());
            
            if ( formatFound.size() == 0 ) {
                return ResponseEntity.badRequest().body("FORMAT IS NOT ALLOWED");
            }

           String formatConfirmed = formatFound.get(0).toString().toLowerCase();
           UUID fileUuid = UUID.randomUUID();  
           String fileNameToCreate =String.format("%s-%s.%s", fileNameParts[0], fileUuid.toString(), formatConfirmed);
           String pathToCreate = String.format("%s/%s", System.getProperty("user.dir").replace("\\","/") + "/" + "samples", fileNameToCreate);
           
           System.out.println(pathToCreate);

           File sampleUpload = new File(pathToCreate);
           if (sampleUpload.createNewFile()) {
            System.out.println("File created: " + sampleUpload.getName());
            } else {
                System.out.println("File already exists.");
            }
            

            if ( sampleUpload.canWrite() && sampleUpload.canRead() ) {
                InputStream is = sample.getInputStream();
                FileUtils.copyInputStreamToFile(is, sampleUpload);
                System.out.println("Write buffer");
                
            } else {
                System.out.println("File access issue, can't write or read.");
            }
           
            
           //InputStream is = sample.getInputStream();
           //File sampleUpload = new File(fileNameToCreate);
           //sampleUpload.createNewFile();

            
            //InputStream is = sample.getInputStream();
            //UUID fileUuid = UUID.randomUUID();            
            //System.out.println(sample.getOriginalFilename());
            //String extension = FilenameUtils.getExtension(inputFile.getOriginalFilename());

            //System.out.println(is);
            return ResponseEntity.ok().body(String.format("Format found => %s", formatConfirmed));
        }    catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported media.", ex);
       }
    }


}
