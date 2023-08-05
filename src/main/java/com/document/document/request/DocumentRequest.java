package com.document.document.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DocumentRequest {
    @NotEmpty(message = "file content can not be empty")
    private String fileContent;

    @Size(max = 255, message = "File name cannot be greater than 255 characters")
    @NotEmpty(message = "File name can not be empty")
    private String fileName;

    @NotEmpty(message = "File extension can not be empty")
    @Pattern(regexp = "\\b(?:txt|docx|word)\\b", message = "Only txt,docx and word are allowed")
    //Done to limit the scope of this project
    private String extension;

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
