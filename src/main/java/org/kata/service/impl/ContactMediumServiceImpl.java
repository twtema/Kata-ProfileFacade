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

    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String PERSONAL = "PERSONAL";
    private static final String BUSINESS = "BUSINESS";

    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;

    public ContactMediumServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }

    @Override
    public List<String> getAllEmail(String icp) {
        return getActualContactMediumsByType(icp, EMAIL);
    }

    @Override
    public List<String> getAllNumberPhone(String icp) {
        return getActualContactMediumsByType(icp, PHONE);
    }

    @Override
    public String getActualPersonalEmail(String icp) {
        return getActualContactMediumByTypeAndUsage(icp, EMAIL, PERSONAL);
    }

    @Override
    public String getActualPersonalNumberPhone(String icp) {
        return getActualContactMediumByTypeAndUsage(icp, PHONE, PERSONAL);
    }

    @Override
    public String getActualBusinessEmail(String icp) {
        return getActualContactMediumByTypeAndUsage(icp, EMAIL, BUSINESS);
    }

    @Override
    public String getActualBusinessNumberPhone(String icp) {
        return getActualContactMediumByTypeAndUsage(icp, PHONE, BUSINESS);
    }

    private List<String> getActualContactMediumsByType(String icp, String type) {
        List<ContactMediumDto> contactMediums = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetContactMedium())
                        .queryParam("icp", icp)
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

    private String getActualContactMediumByTypeAndUsage(String icp, String type, String usage) {
        List<ContactMediumDto> contactMediums = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetContactMedium())
                        .queryParam("icp", icp)
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
