package org.kata.controller;

import com.github.javafaker.Color;
import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.kata.config.UrlProperties;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.exception.IndividualNotFoundException;
import org.kata.service.impl.DocumentServiceImpl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;


@Getter
@Setter
@Slf4j
public class DocumentControllerTest {


    @Test
    public void getActualDocumentsTest() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetActualDocuments("v1/document/getActual");
        urlProperties.setProfileServiceGetNotActualDocuments("v1/document/getNotActual");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));
        ResponseEntity<List<DocumentDto>> response = documentController.getActualDocuments("203-29-3983");
        List<DocumentDto> documents = response.getBody();
//        // создание документа для проверки
//        DocumentDto doc =new DocumentDto("203-29-3983", "FRGN_PASSPORT",
//                "55555555","5872","1997-09-29T20:00:00.000+00:00",
//                "2023-10-25T21:00:00.000+00:00" );


        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(documents.stream().findFirst().orElse(null));

        log.info("Проверка на то что актуальный документ не равен неактуальному документу");
        Assert.assertNotEquals(documents.stream().findFirst().orElse(null)
                , documentController.getNotActualDocuments("203-29-3983").getBody()
                        .stream().findFirst().orElse(null));

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getActualDocuments("203-29-398");
        });
    }


    @Test
    public void getNotActualDocuments() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetActualDocuments("v1/document/getActual");
        urlProperties.setProfileServiceGetNotActualDocuments("v1/document/getNotActual");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getNotActualDocuments("203-29-398");
        });

        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(
            documentController.getNotActualDocuments("203-29-3983")
        );



    }

    @Test
    public void getAllDocuments() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetActualDocuments("v1/document/getActual");
        urlProperties.setProfileServiceGetNotActualDocuments("v1/document/getNotActual");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));

        log.info("Проверка на то что документ не равен null");

        Assert.assertNotNull(
                documentController.getAllDocuments("203-29-3983")
        );

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getAllDocuments("203-29-398");
        });
    }

    @Test
    public void getDocument() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetActualDocuments("v1/document/getActual");
        urlProperties.setProfileServiceGetNotActualDocuments("v1/document/getNotActual");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));


        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(
                documentController.getDocument("203-29-3983",
                        DocumentType.valueOf("RF_DRIVING_LICENSE"))
        );
        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(
                documentController.getDocument("203-29-3983",
                        DocumentType.valueOf("FRGN_PASSPORT"))
        );

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getDocument("203-29-398",
                    DocumentType.valueOf("SNILS"));
        });
    }
}