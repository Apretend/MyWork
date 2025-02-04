package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.FileDirectory;
import com.lyh.storyapi.service.FileDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/fileDirectory")
public class FileDirectoryController {

    @Autowired
    private FileDirectoryService fileDirectoryService;

    // 获取所有文件目录
    @GetMapping("/getAllFileDirectory")
    public ResponseEntity<?> getAllFileDirectory() {
        try {
            List<FileDirectory> fileDirectoryList = fileDirectoryService.getFileDirectory();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", fileDirectoryList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 根据书籍id获取文件目录
    @GetMapping("/getFileDirectoryByBookId/{bookId}")
    public ResponseEntity<?> getFileDirectoryByBookId(@PathVariable int bookId) {
        try {
            List<FileDirectory> fileDirectoryList = fileDirectoryService.getFileDirectoryByBookId(bookId);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", fileDirectoryList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 根据id获取文件目录
    @GetMapping("/getFileDirectoryById/{id}")
    public ResponseEntity<?> getFileDirectoryById(@PathVariable int id) {
        try {
            FileDirectory fileDirectory = fileDirectoryService.getFileDirectoryById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", fileDirectory));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建文件目录
    @PostMapping("/createFileDirectory")
    public ResponseEntity<?> createFileDirectory(@RequestBody FileDirectory fileDirectory) {
        try {
            fileDirectory.setCreateTime(LocalDateTime.now());
            fileDirectory.setUpdateTime(LocalDateTime.now());
            fileDirectoryService.addFileDirectory(fileDirectory);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", fileDirectory));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新文件目录
    @PostMapping("/updateFileDirectory")
    public ResponseEntity<?> updateFileDirectory(@RequestBody FileDirectory fileDirectory) {
        try {
            fileDirectory.setUpdateTime(LocalDateTime.now());
            fileDirectoryService.updateFileDirectory(fileDirectory);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", fileDirectory));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除文件目录
    @GetMapping("/deleteFileDirectory/{id}")
    public ResponseEntity<?> deleteFileDirectory(@PathVariable int id) {
        try {
            fileDirectoryService.deleteFileDirectory(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
