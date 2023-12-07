package org.kata.controller;

import com.github.javafaker.Color;
import lombok.Getter;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import org.junit.Test;
import org.kata.config.UrlProperties;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.impl.DocumentServiceImpl;
import org.springframework.http.ResponseEntity;
import java.util.List;


@Getter
@Setter
@Slf4j
public class DocumentControllerTest {
    @Test
    public void getActualDocumentsTest() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8082/");
        urlProperties.setProfileServiceGetAllDocuments("v1/document/getAll");


        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));
        ResponseEntity<List<DocumentDto>> response = documentController.getActualDocuments("1234567890");
        List<DocumentDto> documents = response.getBody();

        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(documents.stream().findFirst().orElse(null));

        log.info("Проверка на то что актуальный документ не равен неактуальному документу");
        Assert.assertNotEquals(documents.stream().findFirst().orElse(null)
                , documentController.getArchiveDocuments("1234567890").getBody()
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
        urlProperties.setProfileServiceGetAllDocuments("v1/document/getAll");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getArchiveDocuments("203-29-398");
        });

        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(
                documentController.getArchiveDocuments("203-29-3983")
        );


    }

    @Test
    public void getAllDocuments() {
        final UrlProperties urlProperties = new UrlProperties();
        urlProperties.setProfileServiceBaseUrl("http://localhost:8081/");
        urlProperties.setProfileServiceGetAllDocuments("v1/document/getAll");

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
        urlProperties.setProfileServiceGetAllDocuments("v1/document/getAll");

        DocumentController documentController = new DocumentController(new DocumentServiceImpl(urlProperties));


        log.info("Проверка на то что документ не равен null");
        Assert.assertNotNull(
                documentController.getDocument("203-29-3983",
                        DocumentType.valueOf("RF_DRIVING_LICENSE"))
        );
        log.info("Проверка на то что номер документа равен 5555555 ");
        Assert.assertEquals("55555555",
                documentController.getDocument("203-29-3983",
                                DocumentType.valueOf("FRGN_PASSPORT"))
                        .getBody().getDocumentNumber()
        );

        log.info("Проверка на вызов ошибки DocumentsNotFoundException");
        Assert.assertThrows(DocumentsNotFoundException.class, () -> {
            documentController.getDocument("203-29-398",
                    DocumentType.valueOf("SNILS"));
        });
    }
}