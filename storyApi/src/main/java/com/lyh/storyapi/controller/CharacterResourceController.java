package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.CharacterResource;
import com.lyh.storyapi.service.CharacterResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/characterResource")
public class CharacterResourceController {

    @Autowired
    private CharacterResourceService characterResourceService;

    // 获取所有角色资源
    @RequestMapping("/getAllCharacterResource")
    public ResponseEntity<?> getAllCharacterResource() {
        try {
            List<CharacterResource> characterResourceList = characterResourceService.getCharacterResource();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", characterResourceList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过id获取角色资源
    @RequestMapping("/getCharacterResourceById/{id}")
    public ResponseEntity<?> getCharacterResourceById(@PathVariable int id) {
        try {
            CharacterResource characterResource = characterResourceService.getCharacterResourceById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", characterResource));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 通过bookId获取角色资源
    @RequestMapping("/getCharacterResourceByBookId/{bookId}")
    public ResponseEntity<?> getCharacterResourceByBookId(@PathVariable int bookId) {
        try {
            List<CharacterResource> characterResourceList = characterResourceService.getCharacterResourceByBookId(bookId);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", characterResourceList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建角色资源
    @RequestMapping("/createCharacterResource")
    public ResponseEntity<?> createCharacterResource(@RequestBody CharacterResource characterResource) {
        try {
            characterResource.setCreateTime(LocalDateTime.now());
            characterResource.setUpdateTime(LocalDateTime.now());
            characterResourceService.createCharacterResource(characterResource);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新角色资源
    @RequestMapping("/updateCharacterResource")
    public ResponseEntity<?> updateCharacterResource(@RequestBody CharacterResource characterResource) {
        try {
            characterResource.setUpdateTime(LocalDateTime.now());
            characterResourceService.updateCharacterResource(characterResource);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除角色资源
    @RequestMapping("/deleteCharacterResource/{id}")
    public ResponseEntity<?> deleteCharacterResource(@PathVariable int id) {
        try {
            characterResourceService.deleteCharacterResource(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
