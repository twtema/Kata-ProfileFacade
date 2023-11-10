package org.kata;

import org.kata.feignclient.ProfileServiceFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients(clients = ProfileServiceFeignClient.class)
@SpringBootApplication
public class ProfileFacadeApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProfileFacadeApplication.class, args);

    }
}
