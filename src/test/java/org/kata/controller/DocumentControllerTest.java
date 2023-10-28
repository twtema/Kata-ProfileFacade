package org.kata.controller;

import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.kata.config.UrlProperties;
import org.kata.dto.DocumentDto;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.impl.DocumentServiceImpl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.DuplicateFormatFlagsException;
import java.util.List;


@Getter
@Setter

public class DocumentControllerTest {


    @Test

    public void getActualDocumentsTest() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetIndividual("v1/individual");
        urlProperties.setProfileServiceGetActualDocuments("v1/document/getActual");
        urlProperties.setProfileServiceGetNotActualDocuments("v1/document/getNotActual");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));
        ResponseEntity<List<DocumentDto>> response = documentController.getActualDocuments("203-29-3983");
        List<DocumentDto> documents = response.getBody();
        // Выполните утверждения или дополнительную обработку с возвращенными документами
        Assert.assertNotNull(documents);


        // Assert.assertThrows("Documents with icp 203-29-398 not found", Throwable.class, (ThrowingRunnable) documentController.getActualDocuments("203-29-398"));
    }


    @Test
    public void getNotActualDocuments() {

    }
//
//    @Test
//    public void getAllDocuments() {
//    }
//
//    @Test
//    public void getDocumentHandler() {
//    }
}