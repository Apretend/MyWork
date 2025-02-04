package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.BookResource;
import com.lyh.storyapi.service.BookResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookResource")
public class BookResourceController {

    @Autowired
    private BookResourceService bookResourceService;

    // 获取所有书籍资源
    @RequestMapping("/getAllBookResource")
    public ResponseEntity<?> getAllBookResource() {
        try {
            List<BookResource> bookResourceList = bookResourceService.getBookResource();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", bookResourceList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过id获取书籍资源
    @RequestMapping("/getBookResourceById/{id}")
    public ResponseEntity<?> getBookResourceById(@PathVariable int id) {
        try {
            BookResource bookResource = bookResourceService.getBookResourceById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", bookResource));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建书籍资源
    @RequestMapping("/createBookResource")
    public ResponseEntity<?> createBookResource(@RequestBody BookResource bookResource) {
        try {
            bookResource.setCreateTime(LocalDateTime.now());
            bookResource.setUpdateTime(LocalDateTime.now());
            bookResourceService.createBookResource(bookResource);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新书籍资源
    @RequestMapping("/updateBookResource")
    public ResponseEntity<?> updateBookResource(@RequestBody BookResource bookResource) {
        try {
            bookResource.setUpdateTime(LocalDateTime.now());
            bookResourceService.updateBookResource(bookResource);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除书籍资源
    @RequestMapping("/deleteBookResource/{id}")
    public ResponseEntity<?> deleteBookResource(@PathVariable int id) {
        try {
            bookResourceService.deleteBookResource(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
