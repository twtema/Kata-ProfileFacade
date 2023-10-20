package org.kata.controller;

import lombok.RequiredArgsConstructor;
import org.kata.service.DocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/document")
public class DocumentController {
    private final DocumentService documentService;

}
