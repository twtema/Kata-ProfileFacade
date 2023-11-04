package org.kata;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kata.config.UrlProperties;
import org.kata.controller.AddressController;
import org.kata.dto.AddressDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ProfileFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileFacadeApplication.class, args);
    }
}
