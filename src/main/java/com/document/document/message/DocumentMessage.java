package com.document.document.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class DocumentMessage implements Serializable {
    @JsonProperty("id")
    Integer id;

    @JsonProperty("content")
    String content;

    DocumentMessage() {}

    public DocumentMessage(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DocumentMessage{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
