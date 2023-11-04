package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.ContactMediumDto;
import org.kata.dto.enums.ContactMediumType;
import org.kata.exception.ContactMediumNotFoundException;
import org.kata.service.ContactMediumService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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
        List<String> emails = new ArrayList<>();
        List<ContactMediumDto> contactMediumAllEmail = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceBaseUrl())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "Actual Address with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
        assert contactMediumAllEmail != null;
        for (ContactMediumDto email : contactMediumAllEmail) {
            if (email.getType() == ContactMediumType.EMAIL) {
                emails.add(email.getValue());
            }
        }
        return emails;
    }

    @Override
    public String getActualEmail(String icp) {
        List<String> emails = getAllEmail(icp);
        return emails.stream().findFirst().orElseThrow(() ->
                new ContactMediumNotFoundException("No actual email found for icp: " + icp));
    }


    @Override
    public List<String> getAllNumberPhone(String icp) {
        List<String> allPhoneNumbers = new ArrayList<>();
        List<ContactMediumDto> contactMediumAllPhone = loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceBaseUrl())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new ContactMediumNotFoundException(
                                "All Address with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<ContactMediumDto>>() {
                })
                .block();
        assert contactMediumAllPhone != null;
        for (ContactMediumDto allNumber : contactMediumAllPhone) {
            if (allNumber.getType() == ContactMediumType.PHONE) {
                allPhoneNumbers.add(allNumber.getValue());
            }
        }
        return allPhoneNumbers;
    }

    @Override
    public String getActualNumberPhone(String icp) {
        List<String> phone = getAllEmail(icp);
        return phone.stream().findFirst().orElseThrow(() ->
                new ContactMediumNotFoundException("No actual phone found for icp: " + icp));
    }
}

