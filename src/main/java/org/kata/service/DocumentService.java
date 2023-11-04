package org.kata.service;

import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    List<DocumentDto> getArchiveDocuments(String icp);

    List<DocumentDto> getActualDocuments(String icp);

    List<DocumentDto> getAllDocuments(String icp);

    DocumentDto getDocument(String icp, DocumentType documentType);

}
