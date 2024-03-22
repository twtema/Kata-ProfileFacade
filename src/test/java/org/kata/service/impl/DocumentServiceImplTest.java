package org.kata.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.kata.config.UrlProperties;
import org.kata.service.DocumentService;

import static org.junit.Assert.*;

public class DocumentServiceImplTest {
    DocumentService documentService = new DocumentServiceImpl(null);

    @Test

    public void getAllDocumentsTest() {


        Assert.assertNotNull(documentService.getAllDocuments("1234567890", "test-conversationId"));

    }

    @Test
    public void getActualDocumentsTest() {
    }

    @Test
    public void getNoеActualDocumentsTest() {
    }

    @Test
    public void getDocument() {
    }
}