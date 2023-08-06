package com.document.document.service;

import com.document.document.pojo.Document;
import com.document.document.pojo.ProcessedDocument;
import com.document.document.repository.DocumentRepository;
import com.document.document.repository.ProcessedDocumentRepository;
import com.document.document.request.DocumentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @Mock
    DocumentRepository documentRepository;

    @Mock
    ProcessedDocumentRepository processedDocumentRepository;

    @InjectMocks
    DocumentService documentService;

    private DocumentRequest documentRequest;
    private Document document;

    @BeforeEach
    void setup(){
        documentRequest = new DocumentRequest();
        documentRequest.setFileContent("asdf");
        documentRequest.setExtension("txt");
        documentRequest.setFileName("abcd");

        document = new Document();
        document.setFileName(documentRequest.getFileName());
        document.setExtension(documentRequest.getExtension());
        document.setFileContent(documentRequest.getFileContent());
    }

    @Test
    public void canSaveDocument() {

        when(documentRepository.save(any())).thenReturn(document);

        Document savedDocument = documentService.saveDocument(documentRequest);
        assertThat(savedDocument.getExtension()).isEqualTo(documentRequest.getExtension());
        assertThat(savedDocument.getFileName()).isEqualTo(documentRequest.getFileName());
        assertThat(savedDocument.getFileContent()).isEqualTo(documentRequest.getFileContent());
    }

    @Test
    public void subStringCountThrowsExceptionWhenDocumentNotFound(){
        when(documentRepository.findById(any())).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> documentService.getUniqueSubStringCount(1L));
    }

    @Test
    public void subStringCountThrowsExceptionWhenProcessedDocumentNotFound() {
        when(documentRepository.findById(any())).thenReturn(Optional.of(document));
        when(processedDocumentRepository.findById(any())).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> documentService.getUniqueSubStringCount(1L));
    }

    @Test
    public void subStringCountReturnsValueWhenDocumentAndProcessedDocumentAreFound() {
        when(documentRepository.findById(any())).thenReturn(Optional.of(document));
        when(processedDocumentRepository.findById(any())).thenReturn(Optional.of(new ProcessedDocument(1L,6)));
        assertThat(documentService.getUniqueSubStringCount(1L)).isEqualTo(6);
    }
}
