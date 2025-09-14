package com.sbthbt.flightsight_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FlightsightBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightsightBackApplication.class, args);
    }
}