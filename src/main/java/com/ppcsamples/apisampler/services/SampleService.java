package com.ppcsamples.apisampler.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ppcsamples.apisampler.DTO.UserDetailsDTO;
import com.ppcsamples.apisampler.interfaces.ISample;
import com.ppcsamples.apisampler.metadata.SampleFormatEnum;
import com.ppcsamples.apisampler.models.UserSampleModel;
import com.ppcsamples.apisampler.repository.UserSampleRepository;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SampleService implements ISample {
    Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Value("${ppc.dirUpload}")
    String uploadDir;

    UserSampleService userSampleService;
    UserSampleRepository userSampleRepository;

    SampleService(UserSampleService userSampleService, UserSampleRepository userSampleRepository) {
        this.userSampleService = userSampleService;
        this.userSampleRepository = userSampleRepository;
    }

    @Override
    public ResponseEntity<?> uploadSample(MultipartFile sample, String authToken, UserDetailsDTO userDetailsDTO,
            String customFileName) {

        Map<String, Object> response = new HashMap<>();
        try {

            String[] fileNameParts = sample.getOriginalFilename().split("\\.");

            if (fileNameParts.length <= 0) {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("data", "Invalid file");
                return ResponseEntity.badRequest().body(response);
            }

            String sampleExtension = fileNameParts[1];

            SampleFormatEnum[] formats = SampleFormatEnum.values();
            Stream<SampleFormatEnum> streamSampleFormatEnum = Arrays.stream(formats);

            List<SampleFormatEnum> formatFound = streamSampleFormatEnum
                    .filter(e -> e.toString().toLowerCase().contentEquals(sampleExtension))
                    .collect(Collectors.toList());

            if (formatFound.size() == 0) {
                response.put("status", HttpStatus.BAD_REQUEST);
                response.put("data", "Format not allowed");
                return ResponseEntity.badRequest().body(response);
            }

            String formatConfirmed = formatFound.get(0).toString().toLowerCase();
            UUID fileUuid = UUID.randomUUID();
            String fileNameToCreate = String.format("%s-%s.%s", fileNameParts[0], fileUuid.toString(), formatConfirmed);
            String pathToCreate = String.format("%s/%s",
                    System.getProperty("user.dir").replace("\\", "/") + "/" + "samples", fileNameToCreate);
            this.logger.info(String.format("Prepare new file : %s ", pathToCreate));

            File sampleUpload = new File(pathToCreate);
            if (sampleUpload.createNewFile()) {
                this.logger.info(String.format("File is created : %s ", sampleUpload.getName()));
            } else {
                // System.out.println("File already exists.");
            }

            if (sampleUpload.canWrite() && sampleUpload.canRead()) {
                InputStream is = sample.getInputStream();
                this.logger.info(String.format("Write buffer for : %s ", sampleUpload.getName()));
                FileUtils.copyInputStreamToFile(is, sampleUpload);
            } else {
                this.logger.info(String.format("Error while trying to access file (write or read) : %s ",
                        sampleUpload.getName()));
            }
            response.put("status", HttpStatus.OK);
            response.put("data", sampleUpload.getName());

            String mediaFileNameEncoded = URLEncoder.encode(sampleUpload.getName(), StandardCharsets.UTF_8);
            String fullUrl = String.format("%s%s", this.uploadDir, mediaFileNameEncoded);
            URL URLGui = new URL(fullUrl);

            System.out.println(URLGui);

            UUID uuid = UUID.randomUUID();
            UserSampleModel us = new UserSampleModel(URLGui.toString(), userDetailsDTO.getEmail(),
                    userDetailsDTO.getName(), customFileName, "", uuid.toString());
            UserSampleModel newUserSample = this.userSampleService.createUserSample(us);
            return ResponseEntity.ok().body(newUserSample);

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unsupported media", ex);
        }
    }

    @Override
    public ResponseEntity<?> addSampleToAlbum(String albumUuid, String sampleUuid) {
        Map<String, Object> response = new HashMap<>();

        if (albumUuid == null || sampleUuid == null) {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("data", "Invalid albumUuid or sampleUuid (can't be null) ");
            return ResponseEntity.badRequest().body(response);
        }

        UserSampleModel sample = this.userSampleRepository.findBySampleUuid(sampleUuid);
        if (sample == null) {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("data", "Sample not found for this uuid");
            return ResponseEntity.badRequest().body(response);
        }

        sample.setAlbumUuid(albumUuid);
        UserSampleModel sampleUpdated = this.userSampleRepository.save(sample);

        return ResponseEntity.ok(sampleUpdated);
    }

    @Override
    public ResponseEntity<?> getSamplesInputStream(String fileNamWithExtension) {
        String pathToCreate = String.format("%s/%s",
                System.getProperty("user.dir").replace("\\", "/") + "/" + "samples", fileNamWithExtension);
        File sampleUpload = new File(pathToCreate);

        Map<String, Object> response = new HashMap<>();
        if (sampleUpload.canWrite() && sampleUpload.canRead()) {
            try {
                byte[] bytes = FileUtils.readFileToByteArray(sampleUpload);
                String encodedB64 = Base64.getEncoder().encodeToString(bytes);   
                response.put("status", HttpStatus.OK);
                response.put("data", encodedB64);
                response.put("audioSrc", "data:audio/mpeg;base64," + encodedB64);
                return ResponseEntity.ok(response);
            } catch (IOException e) {
                this.logger.error(e.getMessage());
                response.put("status", HttpStatus.BAD_GATEWAY);
                response.put("data", e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
            }
        } else {
            response.put("status", HttpStatus.BAD_REQUEST);
            response.put("data", "File can't be read or write.");
            this.logger.info("File can't be read or write.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
    
}
