package com.document.document.service;

import com.document.document.pojo.Document;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.repository.DocumentRepository;
import com.document.document.repository.ProcessedDocumentRepository;
import com.document.document.request.DocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ProcessedDocumentRepository processedDocumentRepository;

    @Autowired
    public DocumentService(final DocumentRepository documentRepository,
                           final ProcessedDocumentRepository processedDocumentRepository) {
        this.documentRepository = documentRepository;
        this.processedDocumentRepository = processedDocumentRepository;
    }

    private Document saveDocument(final Document document) {
        return documentRepository.save(document);
    }

    public Document saveDocument(final DocumentRequest documentRequest) {
        final Document document = createDocumentFromRequest(documentRequest);
        return saveDocument(document);
    }

    private Document createDocumentFromRequest(final DocumentRequest documentRequest) {
        final Document document = new Document();
        document.setFileContent(documentRequest.getFileContent());
        document.setExtension(documentRequest.getExtension());
        document.setFileName(documentRequest.getFileName());
        return document;
    }

    public Integer getUniqueSubStringCount(final Long documentId) {
        documentRepository.findById(documentId)
                .orElseThrow(()-> new EntityNotFoundException("No document found with id: " + documentId));

        final ProcessedDocument processedDocument = findProcessedDocumentById(documentId);
        return processedDocument.getUniqueNumber();
    }

    public ProcessedDocument findProcessedDocumentById(final Long documentId) {
        return processedDocumentRepository.findById(documentId)
                .orElseThrow(()-> new EntityNotFoundException("No document found with id: " + documentId));
    }
}
