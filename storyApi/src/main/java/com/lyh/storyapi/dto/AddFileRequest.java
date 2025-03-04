package com.lyh.storyapi.dto;

public class AddFileRequest {

    private int bookId;
    private Integer directoryId;
    private String directoryName;
    private Integer documentId;
    private String documentName;
    private String documentContent;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }
}
