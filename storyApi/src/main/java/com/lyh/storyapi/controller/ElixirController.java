package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Elixir;
import com.lyh.storyapi.service.ElixirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/elixir")
public class ElixirController {

    @Autowired
    private ElixirService elixirService;

    // 获取所有丹药
    @GetMapping("/getAllElixir")
    public ResponseEntity<?> getAllElixir()
    {
        try {
            List<Elixir> elixirList = elixirService.getElixir();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", elixirList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过id获取丹药
    @GetMapping("/getElixirById/{id}")
    public ResponseEntity<?> getElixirById(@PathVariable int id)
    {
        try {
            Elixir elixir = elixirService.getElixirById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", elixir));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过书籍id获取丹药
    @GetMapping("/getElixirsByBookId/{bookId}")
    public ResponseEntity<?> getElixirsByBookId(@PathVariable int bookId)
    {
        try {
            List<Elixir> elixirList = elixirService.getElixirsByBookId(bookId);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", elixirList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建丹药
    @PostMapping("/createElixir")
    public ResponseEntity<?> createElixir(@RequestBody Elixir elixir)
    {
        try {
            elixir.setCreateTime(LocalDateTime.now());
            elixir.setUpdateTime(LocalDateTime.now());
            elixirService.createElixir(elixir);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", elixir));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新丹药
    @PostMapping("/updateElixir")
    public ResponseEntity<?> updateElixir(@RequestBody Elixir elixir)
    {
        try {
            elixir.setUpdateTime(LocalDateTime.now());
            elixirService.updateElixir(elixir);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", elixir));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除丹药
    @GetMapping("/deleteElixir/{id}")
    public ResponseEntity<?> deleteElixir(@PathVariable int id)
    {
        try {
            elixirService.deleteElixir(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
