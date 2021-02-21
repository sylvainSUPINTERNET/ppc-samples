package com.ppcsamples.apisampler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ApiSamplerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSamplerApplication.class, args);
	}

}
