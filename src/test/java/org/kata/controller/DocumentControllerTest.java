package org.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.service.MaskingService;
import org.kata.service.impl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @MockBean
    private DocumentServiceImpl documentService;

    @MockBean
    private MaskingService maskingService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    String conversationId;
    String icp;
    DocumentDto documentOne;
    DocumentDto documentTwo;
    DocumentDto maskedDocumentOne;
    List<DocumentDto> documents;

    @Before
    public void setUp() {

        conversationId = "conversationId";
        icp = "1234567890";
        documentOne = DocumentDto.builder()
                .icp("1234567890")
                .documentType(DocumentType.RF_PASSPORT)
                .actual(true)
                .documentNumber("1234567890")
                .documentSerial("123")
                .build();
        documentTwo = DocumentDto.builder()
                .icp("1234567890")
                .documentType(DocumentType.RF_PASSPORT)
                .actual(true)
                .documentNumber("1234567890")
                .documentSerial("123")
                .build();
        documents = List.of(documentOne, documentTwo);

        maskedDocumentOne = DocumentDto.builder()
                .icp("1234567890")
                .documentType(DocumentType.RF_PASSPORT)
                .actual(true)
                .documentNumber("12****90")
                .documentSerial("1****3")
                .build();
    }

    @Test
    public void shouldReturnActualDocumentsWhenTheyExist() throws Exception {
        when(documentService.getActualDocuments(anyString(), anyString())).thenReturn(documents);
        mockMvc.perform(get("/v1/document/getActual")
                        .header("conversationId", conversationId)
                        .param("icp", icp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentNumber").value(documentOne.getDocumentNumber()))
                .andExpect(jsonPath("$[0].documentType").value(documentOne.getDocumentType().toString()))
                .andExpect(jsonPath("$[0].actual").value(documentOne.isActual()))
                .andExpect(jsonPath("$[1].documentNumber").value(documentTwo.getDocumentNumber()))
                .andExpect(jsonPath("$[1].documentType").value(documentTwo.getDocumentType().toString()))
                .andExpect(jsonPath("$[1].actual").value(documentTwo.isActual()));

        verify(documentService, times(1)).getActualDocuments(anyString(), anyString());
    }

    @Test
    public void testGetActualDocumentsWhenNoDocumentsExist() throws Exception {
        when(documentService.getActualDocuments(anyString(), anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/document/getActual")
                        .header("conversationId", conversationId)
                        .param("icp", icp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void shouldReturnBadRequestWhenIcpIsMissing() throws Exception {
        mockMvc.perform(get("/v1/document/getActual").header("conversationId", conversationId))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnMaskedDocumentWhenMaskingIsRequired() throws Exception {
        when(documentService.getDocument(anyString(), any(), anyString())).thenReturn(documentOne);
        when(maskingService.maskPersonalDataGeneric(any())).thenReturn(maskedDocumentOne);
        mockMvc.perform(get("/v1/document/getDocument")
                        .header("conversationId", conversationId)
                        .param("icp", icp)
                        .param("documentType", DocumentType.RF_PASSPORT.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentNumber").value(maskedDocumentOne.getDocumentNumber()))
                .andExpect(jsonPath("$.documentType").value(maskedDocumentOne.getDocumentType().toString()))
                .andExpect(jsonPath("$.actual").value(documentOne.isActual()));
    }

    @Test
    public void shouldReturnBadRequestWhenDocumentTypeIsMissing() throws Exception {
        mockMvc.perform(get("/v1/document/getDocument")
                        .header("conversationId", conversationId)
                        .param("icp", icp))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnAllDocumentsWhenTheyExist() throws Exception {
        when(documentService.getAllDocuments(anyString(), anyString())).thenReturn(documents);
        mockMvc.perform(get("/v1/document/getAll")
                        .header("conversationId", conversationId)
                        .param("icp", icp))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentNumber").value(documentOne.getDocumentNumber()))
                .andExpect(jsonPath("$[0].documentType").value(documentOne.getDocumentType().toString()))
                .andExpect(jsonPath("$[0].actual").value(documentOne.isActual()))
                .andExpect(jsonPath("$[1].documentNumber").value(documentTwo.getDocumentNumber()))
                .andExpect(jsonPath("$[1].documentType").value(documentTwo.getDocumentType().toString()))
                .andExpect(jsonPath("$[1].actual").value(documentTwo.isActual()));

        verify(documentService, times(1)).getAllDocuments(anyString(), anyString());
    }

    @Test
    public void shouldReturnBadRequestWhenIcpIsMissingForAllDocuments() throws Exception {
        mockMvc.perform(get("/v1/document/getAll").header("conversationId", conversationId))
                .andExpect(status().isBadRequest());
    }
}