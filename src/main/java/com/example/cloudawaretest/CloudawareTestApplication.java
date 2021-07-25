package com.example.cloudawaretest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudawareTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudawareTestApplication.class, args);
    }

}
