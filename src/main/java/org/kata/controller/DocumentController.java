package org.kata.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kata.dto.DocumentDto;
import org.kata.dto.enums.DocumentType;
import org.kata.exception.DocumentsNotFoundException;
import org.kata.service.DocumentService;
import org.kata.service.MaskingService;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Documents", description = "This class is designed to work with Individual documents")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/document")
public class DocumentController {
    private final DocumentService documentService;
    private final MaskingService maskingService;

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
        DocumentDto document = documentService.getDocument(icp, documentType);
        return new ResponseEntity<>(maskingService.maskPersonalDataGeneric(document), HttpStatus.OK);
    }

    /**
     * Данный контроллер по icp сущности Individual возвращает его <b>RF Passport</b>
     * @param icp Individual's ICP
     * @return Individual's RF Passport in {@link DocumentDto} format
     */
    @Operation(summary = "Get Individual's RF Passport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument/RFPassport")
    public ResponseEntity<DocumentDto> getRFPassport
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getDocument(icp, DocumentType.RF_PASSPORT), HttpStatus.OK);
    }

    /**
     * Данный контроллер по icp сущности Individual возвращает его <b>FRGN Passport</b>
     * @param icp Individual's ICP
     * @return Individual's FRGN Passport in {@link DocumentDto} format
     */
    @Operation(summary = "Get Individual's Foreign Passport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument/FRGNPassport")
    public ResponseEntity<DocumentDto> getFRGNPASSPORT
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getDocument(icp, DocumentType.FRGN_PASSPORT), HttpStatus.OK);
    }

    /**
     * Данный контроллер по icp сущности Individual возвращает его <b>INN</b>
     * @param icp Individual's ICP
     * @return Individual's INN in {@link DocumentDto} format
     */
    @Operation(summary = "Get Individual's INN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument/INN")
    public ResponseEntity<DocumentDto> getINN
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getDocument(icp, DocumentType.INN), HttpStatus.OK);
    }

    /**
     * Данный контроллер по icp сущности Individual возвращает его <b>SNILS</b>
     * @param icp Individual's ICP
     * @return Individual's SNILS in {@link DocumentDto} format
     */
    @Operation(summary = "Get Individual's SNILS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument/SNILS")
    public ResponseEntity<DocumentDto> getSNILS
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getDocument(icp, DocumentType.SNILS), HttpStatus.OK);
    }

    /**
     * Данный контроллер по icp сущности Individual возвращает его <b>RF Driving License</b>
     * @param icp Individual's ICP
     * @return Individual's RF Driving License in {@link DocumentDto} format
     */
    @Operation(summary = "Get Individual's RF Driving License")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of document"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getDocument/RFDrivingLicense")
    public ResponseEntity<DocumentDto> getRFDrivingLicense
            (@Parameter(description = "ICP identifier", required = true) @RequestParam String icp) {
        return new ResponseEntity<>(documentService.getDocument(icp, DocumentType.RF_DRIVING_LICENSE), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DocumentsNotFoundException.class)
    public ErrorMessage getDocumentHandler(DocumentsNotFoundException e) {
        return new ErrorMessage(e.getMessage());
    }
}