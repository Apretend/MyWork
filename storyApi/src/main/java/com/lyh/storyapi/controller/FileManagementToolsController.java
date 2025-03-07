package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.AddFileRequest;
import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Book;
import com.lyh.storyapi.model.Document;
import com.lyh.storyapi.model.FileDirectory;
import com.lyh.storyapi.service.BookService;
import com.lyh.storyapi.service.DocumentService;
import com.lyh.storyapi.service.FileDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fileManagementTools")
public class FileManagementToolsController {

    @Autowired
    private DocumentService documentService;
    @Autowired
    private FileDirectoryService fileDirectoryService;
    @Autowired
    private BookService bookService;

    // 获取指定书的目录
    @GetMapping("/getFileByBookId/{bookId}")
    public ResponseEntity<?> getFileByBookId(@PathVariable("bookId") int bookId) {
        try {
            List<FileDirectory> fileDirectoryList = fileDirectoryService.getFileDirectoryByBookId(bookId);
            Map<Integer, Map<String, Object>> directoryMap = new HashMap<>();
            for (FileDirectory directory : fileDirectoryList) {
                Map<String, Object> dirNode = new HashMap<>();
                dirNode.put("id", directory.getDirectoryId());
                dirNode.put("name", directory.getDirectoryName());
                dirNode.put("children", new ArrayList<>());
                dirNode.put("type", "directory");
                directoryMap.put(directory.getDirectoryId(), dirNode);
            }
            System.out.println(directoryMap);
            for (FileDirectory directory : fileDirectoryList) {
                List<Document> documents = documentService.getDocumentByDirectoryId(directory.getDirectoryId());
                List<Map<String, Object>> children = (List<Map<String, Object>>) directoryMap.get(directory.getDirectoryId()).get("children");

                for (Document document : documents){
                    Map<String, Object> fileNode = new HashMap<>();
                    fileNode.put("id", document.getDocumentId());
                    fileNode.put("name", document.getDocumentName());
                    fileNode.put("title", document.getDocumentTitle());
                    fileNode.put("children", new ArrayList<>());
                    fileNode.put("type", "file");
                    children.add(fileNode);
                }
            }

            List<Map<String, Object>> treeData = new ArrayList<>();
            for (Map<String, Object> dir : directoryMap.values()) {
                treeData.add(dir);
            }
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", treeData));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 新增目录
    @PostMapping("/addDirectory")
    public ResponseEntity<?> addDirectory(@RequestBody AddFileRequest addFileRequest) {
        try {
            Book book = bookService.getBookById(addFileRequest.getBookId());
            if (book != null) {
                FileDirectory fileDirectory = new FileDirectory();
                fileDirectory.setCreateTime(LocalDateTime.now());
                fileDirectory.setUpdateTime(LocalDateTime.now());
                fileDirectory.setBookId(addFileRequest.getBookId());
                fileDirectory.setDirectoryName(addFileRequest.getDirectoryName());
                fileDirectoryService.addFileDirectory(fileDirectory);
                return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
            } else {
                return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "Book not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 新增文档
    @PostMapping("/addDoucment")
    public ResponseEntity<?> addFile(@RequestBody AddFileRequest addFileRequest) {
        try {
            FileDirectory fileDirectory = fileDirectoryService.getFileDirectoryById(addFileRequest.getDirectoryId());
            if (fileDirectory != null) {
                Document document = new Document();
                document.setCreateTime(LocalDateTime.now());
                document.setUpdateTime(LocalDateTime.now());
                document.setDocumentContent(addFileRequest.getDocumentContent());
                document.setDocumentName(addFileRequest.getDocumentName());
                document.setDirectoryId(addFileRequest.getDirectoryId());
                document.setDocumentTitle(addFileRequest.getDocumentTitle());
                documentService.createDocument(document);
                return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
            } else {
                return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "Book not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 重命名书
    @PostMapping("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody AddFileRequest addFileRequest) {
        try {
            Book book = bookService.getBookById(addFileRequest.getBookId());
            if (book != null) {
                book.setBookName(addFileRequest.getBookName());
                book.setUpdateTime(LocalDateTime.now());
                bookService.updateBook(book);
                return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success",book));
            } else {
                return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "Book not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }
    // 更新文档或者重命名目录
    @PostMapping("/updateFile")
    public ResponseEntity<?> updateFile(@RequestBody AddFileRequest addFileRequest) {
        try {
            if (addFileRequest.getDocumentId() != null) {
                Document document = documentService.getDocumentById(addFileRequest.getDocumentId());
                if (document != null) {
                    document.setDocumentContent(addFileRequest.getDocumentContent());
                    document.setDocumentName(addFileRequest.getDocumentName());
                    document.setDocumentTitle(addFileRequest.getDocumentTitle());
                    document.setUpdateTime(LocalDateTime.now());
                    documentService.updateDocument(document);
                    return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
                } else {
                    return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "Document not found"));
                }
            } else {
                FileDirectory fileDirectory = fileDirectoryService.getFileDirectoryById(addFileRequest.getDirectoryId());
                if (fileDirectory != null) {
                    fileDirectory.setDirectoryName(addFileRequest.getDirectoryName());
                    fileDirectory.setUpdateTime(LocalDateTime.now());
                    fileDirectoryService.updateFileDirectory(fileDirectory);
                    return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
                } else {
                    return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "FileDirectory not found"));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除文件
    @PostMapping("/deleteFile")
    public ResponseEntity<?> deleteFile(@RequestBody AddFileRequest addFileRequest) {
        try {
            if (addFileRequest.getDocumentId() != null) {
                Document document = documentService.getDocumentById(addFileRequest.getDocumentId());
                if (document != null) {
                    documentService.deleteDocument(addFileRequest.getDocumentId());
                    return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
                } else {
                    return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "Document not found"));
                }
            } else {
                FileDirectory fileDirectory = fileDirectoryService.getFileDirectoryById(addFileRequest.getDirectoryId());
                if (fileDirectory != null) {
                    List<Document> documents = documentService.getDocumentByDirectoryId(addFileRequest.getDirectoryId());
                    for (Document document : documents) {
                        documentService.deleteDocument(document.getDocumentId());
                    }
                    fileDirectoryService.deleteFileDirectory(addFileRequest.getDirectoryId());
                    return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
                } else {
                    return ResponseEntity.status(200).body(new ApiResponse<>(-1, "Error", "FileDirectory not found"));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
