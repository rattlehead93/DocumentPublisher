package com.document.document.controller;

import com.document.document.message.DocumentMessage;
import com.document.document.pojo.Document;
import com.document.document.publisher.DocumentPublisher;
import com.document.document.request.DocumentRequest;
import com.document.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentPublisher publisher;

    @Autowired
    public DocumentController(final DocumentService documentService,
                              final DocumentPublisher documentPublisher) {
        this.documentService = documentService;
        this.publisher = documentPublisher;
    }

    @PostMapping("/documents")
    public ResponseEntity saveDocument(@Valid @RequestBody final DocumentRequest documentRequest) {
        final Document document = documentService.saveDocument(documentRequest);

        publisher.sendMessage(new DocumentMessage(document.getId(), document.getFileContent()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/documents/{id}/count")
    public ResponseEntity<Integer> findDocument(@PathVariable final Long id) {
        Integer uniqueCount = documentService.getUniqueSubStringCount(id);
        return new ResponseEntity<>(uniqueCount, HttpStatus.OK);
    }
}
