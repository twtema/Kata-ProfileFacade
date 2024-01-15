package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.ContactMediumDto;
import org.kata.exception.ContactMediumNotFoundException;
import org.kata.service.ContactMediumService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class ContactMediumServiceImpl implements ContactMediumService {

    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;

    public ContactMediumServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }

    @Override
    public List<String> getAllEmail(String icp) {
        var type = "EMAIL";
        return getActualContactMediumsByType(icp, type);
    }

    @Override
    public String getActualPersonalEmail(String icp) {
        var type = "EMAIL";
        var usage = "PERSONAL";
        return getActualContactMediumByTypeAndUsage(icp, type, usage);
    }

    @Override
    public List<String> getAllNumberPhone(String icp) {
        var type = "PHONE";
        return getActualContactMediumsByType(icp, type);
    }

    @Override
    public String getActualPersonalNumberPhone(String icp) {
        var type = "PHONE";
        var usage = "PERSONAL";
        return getActualContactMediumByTypeAndUsage(icp, type, usage);
    }

    @Override
    public String getActualBusinessEmail(String icp) {
        var type = "EMAIL";
        var usage = "BUSINESS";
        return getActualContactMediumByTypeAndUsage(icp, type, usage);
    }

    @Override
    public String getActualBusinesslNumberPhone(String icp) {
        var type = "PHONE";
        var usage = "BUSINESS";
        return getActualContactMediumByTypeAndUsage(icp, type, usage);
    }

    private List<String> getActualContactMediumsByType(String icp, String type) {
        List<ContactMediumDto> contactMediums = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetContactMedium())
                        .queryParam("id", icp)
                        .queryParam("type", type)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "ContactMedium with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
        assert contactMediums != null;
        return contactMediums.stream()
                .map(ContactMediumDto::getValue)
                .toList();
    }

    private List<String> getActualContactMediumsByUsage(String icp, String usage) {
        List<ContactMediumDto> contactMediums = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetContactMedium())
                        .queryParam("id", icp)
                        .queryParam("usage", usage)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "ContactMedium with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
        assert contactMediums != null;
        return contactMediums.stream()
                .map(ContactMediumDto::getValue)
                .toList();
    }

    private String getActualContactMediumByTypeAndUsage(String icp, String type, String usage) {
        List<ContactMediumDto> contactMediums = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetContactMedium())
                        .queryParam("id", icp)
                        .queryParam("type", type)
                        .queryParam("usage", usage)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "ContactMedium with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
        assert contactMediums != null;
        return contactMediums.stream()
                .map(ContactMediumDto::getValue)
                .findFirst()
                .orElseThrow(() -> new ContactMediumNotFoundException("No contact mediums with this parameters found for icp: " + icp));
    }
}
