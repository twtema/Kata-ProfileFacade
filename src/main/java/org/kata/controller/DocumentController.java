package org.kata.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Documents", description = "This class is designed to work with Individual documents")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/document")
public class DocumentController {
    private final DocumentService documentService;

    @Operation(summary = "Get actual documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of actual documents"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getActual")
    public ResponseEntity<List<DocumentDto>> getActualDocuments(@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getActualDocuments(icp), HttpStatus.OK);
    }

    @Operation(summary = "Get archive documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of archive documents"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getArchive")
    public ResponseEntity<List<DocumentDto>> getArchiveDocuments(@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getArchiveDocuments(icp), HttpStatus.OK);
    }

    @Operation(summary = "Get all documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of all documents"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<DocumentDto>> getAllDocuments
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getAllDocuments(icp), HttpStatus.OK);
    }

    @Operation(summary = "Get document by individual and document type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument")
    public ResponseEntity<DocumentDto> getDocument
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp,
             @Parameter(description = "Document type", required = true) @RequestParam DocumentType documentType) {
        return new ResponseEntity<>(documentService.getDocument(icp, documentType), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DocumentsNotFoundException.class)
    public ErrorMessage getDocumentHandler(DocumentsNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}