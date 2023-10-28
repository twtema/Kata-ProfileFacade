package org.kata.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kata.config.UrlProperties;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * This class is designed to work with individual documents.
 */
@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final UrlProperties urlProperties;
    private final WebClient loaderWebClient;


    public DocumentServiceImpl(UrlProperties urlProperties) {
        this.urlProperties = urlProperties;
        this.loaderWebClient = WebClient.create(urlProperties.getProfileServiceBaseUrl());
    }


    /**
     * Method to get a list of all documents by icp.
     *
     * @param icp - Individual's icp.
     * @return List of DocumentDto.
     */
    public List<DocumentDto> getActualDocuments(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetActualDocuments())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDto>>() {
                })
                .block();
    }

    /**
     * Method to get a list of all documents by icp.
     *
     * @param icp - Individual's icp.
     * @return List of DocumentDto.
     */
    @Override
    public List<DocumentDto> getAllDocuments(String icp) {
        List<DocumentDto> allDocuments = new ArrayList<>(getActualDocuments(icp));
        allDocuments.addAll(getNotActualDocuments(icp));

        return allDocuments;
    }

    /**
     * Method to get a list of not actual documents by icp.
     *
     * @param icp - Individual's icp.
     * @return List of DocumentDto.
     */

    @Override
    public List<DocumentDto> getNotActualDocuments(String icp) {
        return loaderWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(urlProperties.getProfileServiceGetNotActualDocuments())
                        .queryParam("icp", icp)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, response ->
                        Mono.error(new DocumentsNotFoundException(
                                "Documents with icp " + icp + " not found")
                        )
                )
                .bodyToMono(new ParameterizedTypeReference<List<DocumentDto>>() {
                })
                .block();
    }

    /**
     * Method to get an individual document by icp and document type.
     *
     * @param icp          - Individual's icp.
     * @param documentType - Document type.
     * @return DocumentDto.
     * @throws DocumentsNotFoundException if the document with
     *                                    the specified icp and document type is not found.
     */
    @Override
    public DocumentDto getDocument(String icp, DocumentType documentType) {
        return getAllDocuments(icp).stream()
                .filter(Document -> Document.getDocumentType()
                        .equals(documentType)).findFirst()
                .orElseThrow(() -> new DocumentsNotFoundException(
                        "Documents with icp " + icp + ", documentType "
                                + documentType + " not found"));
    }
}
