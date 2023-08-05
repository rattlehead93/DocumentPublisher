package com.document.document.controller;

import com.document.document.publisher.DocumentPublisher;
import com.document.document.request.DocumentRequest;
import com.document.document.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DocumentController.class)
class DocumentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private DocumentPublisher documentPublisher;

    private DocumentRequest documentRequest;

    @BeforeEach
    void document() {
        documentRequest = new DocumentRequest();
        documentRequest.setExtension("txt");
        documentRequest.setFileContent("abc");
        documentRequest.setFileName("filename");
    }

    public MvcResult getInvalidRequestResult() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .post("/documents")
                        .content(objectMapper.writeValueAsString(documentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
    }

    public MvcResult getValidRequestResult() throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                        .post("/documents")
                        .content(objectMapper.writeValueAsString(documentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void returnsBadRequest_whenInvalidExtension() throws Exception {
        documentRequest.setExtension("abcd");
        MvcResult result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("Only txt,docx and word are allowed");
    }

    @Test
    public void returnsBadRequest_whenInvalidFileContent() throws Exception {
        documentRequest.setFileContent("");
        MvcResult result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("file content can not be empty");

        documentRequest.setFileContent(null);
        result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("file content can not be empty");
    }

    @Test
    public void returnsBadRequest_whenInvalidFileName() throws Exception {
        documentRequest.setFileName("");
        MvcResult result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("File name can not be empty");

        documentRequest.setFileName(null);
        result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("File name can not be empty");

        documentRequest.setFileName(new String("a").repeat(256));
        result = getInvalidRequestResult();
        assertThat(result.getResponse().getContentAsString()).contains("File name cannot be greater than 255 characters");
    }

}
