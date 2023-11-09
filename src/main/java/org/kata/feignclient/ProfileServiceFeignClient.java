package org.kata.feignclient;

import org.kata.dto.IndividualDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//todo url задать через настройки
@FeignClient(name = "profileService", url = "http://localhost:8082")
public interface ProfileServiceFeignClient {

    @GetMapping("${url-properties.profileServiceGetIndividual}")
    IndividualDto getIndividual(@RequestParam String icp);
}
