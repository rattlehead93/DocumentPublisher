package com.document.document.controller;

import com.document.document.request.DocumentRequest;
import com.document.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {
    private final DocumentService documentService;

    @Autowired
    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/documents")
    public ResponseEntity saveDocument(@RequestBody final DocumentRequest documentRequest) {
        documentService.saveDocument(documentService.createDocumentFromRequest(documentRequest));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
