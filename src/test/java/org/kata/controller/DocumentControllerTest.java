package org.kata.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentService;
import org.kata.service.MaskingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @Mock
    private MaskingService maskingService;

    @InjectMocks
    private DocumentController documentController;

    private List<DocumentDto> documentList;

    private DocumentDto document1;
    private DocumentDto document2;

    @Before
    public void setUp() {
        document1 = DocumentDto.builder()
                .documentType(DocumentType.RF_PASSPORT)
                .actual(true)
                .build();

        document2 = DocumentDto.builder()
                .documentType(DocumentType.INN)
                .actual(true)
                .build();

        documentList = Arrays.asList(document1, document2);
    }

    @Test
    public void shouldReturnActualDocuments_WhenActualDocumentsExist() {
        when(documentService.getActualDocuments(anyString(), anyString())).thenReturn(documentList);

        List<DocumentDto> actualDocuments = documentController.getActualDocuments("1234567890", "test-conversationId").getBody();

        assertNotNull(actualDocuments);
        assertEquals(2, actualDocuments.size());
    }


    @Test(expected = DocumentsNotFoundException.class)
    public void shouldThrowDocumentsNotFoundException_WhenNoDocumentsExist() {
        when(documentService.getActualDocuments(anyString(), anyString())).thenThrow(new DocumentsNotFoundException("Documents not found"));

        documentController.getActualDocuments("1234567890", "test-conversationId");
    }

    @Test
    public void shouldReturnArchiveDocuments_WhenArchiveDocumentsExist() {
        when(documentService.getArchiveDocuments(anyString(), anyString())).thenReturn(documentList);

        List<DocumentDto> archiveDocuments = documentController.getArchiveDocuments("1234567890", "test-conversationId").getBody();

        assertNotNull(archiveDocuments);
        assertEquals(2, archiveDocuments.size());
    }

    @Test(expected = DocumentsNotFoundException.class)
    public void shouldThrowDocumentsNotFoundException_WhenNoArchiveDocumentsExist() {
        when(documentService.getArchiveDocuments(anyString(), anyString())).thenThrow(new DocumentsNotFoundException("Documents not found"));

        documentController.getArchiveDocuments("1234567890", "test-conversationId");
    }

    @Test
    public void shouldReturnAllDocuments_WhenDocumentsExist() {
        when(documentService.getAllDocuments(anyString(), anyString())).thenReturn(documentList);

        List<DocumentDto> allDocuments = documentController.getAllDocuments("1234567890", "test-conversationId").getBody();

        assertNotNull(allDocuments);
        assertEquals(2, allDocuments.size());
    }

    @Test
    public void shouldReturnDocument_WhenDocumentExists() {

        DocumentDto expectedDocument = DocumentDto.builder()
                .icp("1234567890")
                .documentType(DocumentType.RF_PASSPORT)
                .build();
        when(documentService.getDocument(anyString(), any(), anyString())).thenReturn(expectedDocument);

        DocumentDto actualDocument = documentController.getDocument("test-conversationId", "1234567890", DocumentType.RF_PASSPORT).getBody();

        assertNotNull(actualDocument);
        assertEquals(expectedDocument, actualDocument);
    }

    @Test(expected = DocumentsNotFoundException.class)
    public void shouldThrowDocumentsNotFoundException_WhenDocumentDoesNotExist() {
        when(documentService.getDocument(anyString(), any(), anyString())).thenThrow(new DocumentsNotFoundException("Document not found"));

        documentController.getDocument("test-conversationId", "1234567890", DocumentType.RF_PASSPORT);
    }

//    @Test
//    public void getNotActualDocuments_ReturnsEmptyList_WhenNoDocumentsAreNotActual() {
//        when(documentService.getActualDocuments(anyString(), anyString())).thenReturn(Collections.emptyList());
//
//        List<DocumentDto> actualDocuments = documentController.getActualDocuments("1234567890", "test-conversationId").getBody();
//
//        assertNotNull(actualDocuments);
//        assertTrue(actualDocuments.isEmpty());
//    }
//
//    @Test
//    public void getNotActualDocuments_ReturnsCorrectDocuments_WhenDocumentsAreNotActual() {
//        DocumentDto notActualDocument = DocumentDto.builder()
//                .documentType(DocumentType.RF_PASSPORT)
//                .actual(false)
//                .build();
//
//        List<DocumentDto> notActualDocuments = Arrays.asList(notActualDocument);
//
//        when(documentService.getActualDocuments(anyString(), anyString())).thenReturn(notActualDocuments);
//
//        List<DocumentDto> actualDocuments = documentController.getActualDocuments("1234567890", "test-conversationId").getBody();
//
//        assertNotNull(actualDocuments);
//        assertEquals(1, actualDocuments.size());
//        assertEquals(notActualDocument, actualDocuments.get(0));
//    }


}