package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Document;
import com.lyh.storyapi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // 获取所有文档
    @RequestMapping("/getAllDocument")
    public ResponseEntity<?> getAllDocument() {
        try {
            List<Document> documents = documentService.getDocument();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", documents));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过目录id获取文档
    @RequestMapping("/getDocumentByDirectoryId/{directoryId}")
    public ResponseEntity<?> getDocumentByDirectoryId(@PathVariable int directoryId) {
        try {
            List<Document> documents = documentService.getDocumentByDirectoryId(directoryId);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", documents));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过id获取文档
    @RequestMapping("/getDocumentById/{id}")
    public ResponseEntity<?> getDocumentById(@PathVariable int id) {
        try {
            Document document = documentService.getDocumentById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", document));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建文档
    @RequestMapping("/createDocument")
    public ResponseEntity<?> createDocument(@RequestBody Document document) {
        try {
            document.setCreateTime(LocalDateTime.now());
            document.setUpdateTime(LocalDateTime.now());
            documentService.createDocument(document);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新文档
    @RequestMapping("/updateDocument")
    public ResponseEntity<?> updateDocument(@RequestBody Document document) {
        try {
            document.setUpdateTime(LocalDateTime.now());
            documentService.updateDocument(document);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除文档
    @RequestMapping("/deleteDocument/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable int id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
