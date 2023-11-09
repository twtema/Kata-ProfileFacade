package org.kata;


import org.kata.feignclient.ProfileServiceFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ApplicationContext;


@EnableFeignClients(clients = ProfileServiceFeignClient.class)
@SpringBootApplication
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class ProfileFacadeApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ProfileFacadeApplication.class, args);
        var client = context.getBean(ProfileServiceFeignClient.class);
        System.out.println(client.getHello());
    }

}
