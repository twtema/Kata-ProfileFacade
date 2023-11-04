package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.AddressDto;
import org.kata.exception.AddressNotFoundException;
import org.kata.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;

    public AddressServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }

    @Override
    public List<AddressDto> getAllAddresses(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetAddress())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new AddressNotFoundException(
                                "All Addresses with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<AddressDto>>() {
                })
                .block();
    }

    @Override
    public AddressDto getActualAddress(String icp) {
        return getAllAddresses(icp).stream().filter(AddressDto::isActual).findFirst().orElse(null);
    }
}
