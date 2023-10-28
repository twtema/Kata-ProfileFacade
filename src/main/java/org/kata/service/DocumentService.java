package org.kata.service;

import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    // To Do
//    Должны быть эндпоинты которые возвращают Список документов и
//    документ в его еденичном представлении
//    Список актуальных документов
//    Список не актуальных документов

    List<DocumentDto> getNotActualDocuments(String icp);

    List<DocumentDto> getActualDocuments(String icp);

    List<DocumentDto> getAllDocuments(String icp);

    DocumentDto getDocument(String icp, DocumentType documentType);

}
