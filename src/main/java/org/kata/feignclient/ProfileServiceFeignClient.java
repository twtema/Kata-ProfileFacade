package org.kata.feignclient;

import org.kata.dto.IndividualDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "profileService", url = "${url-properties.profileServiceBaseUrl}")
public interface ProfileServiceFeignClient {

    @GetMapping("v1/individual")
    IndividualDto getIndividual(@RequestParam(name = "icp") String icp);

}
