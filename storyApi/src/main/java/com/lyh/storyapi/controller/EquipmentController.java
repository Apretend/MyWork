package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Equipment;
import com.lyh.storyapi.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    // 获取所有装备
    @GetMapping("/getAllEquipment")
    public ResponseEntity<?> getAllEquipment() {
        try {
            List<Equipment> equipmentList = equipmentService.getEquipment();
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", equipmentList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 根据书籍id获取装备
    @GetMapping("/getEquipmentsByBookId/{bookId}")
    public ResponseEntity<?> getEquipmentsByBookId(@PathVariable int bookId) {
        try {
            List<Equipment> equipmentList = equipmentService.getEquipmentsByBookId(bookId);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", equipmentList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 根据id获取装备
    @GetMapping("/getEquipmentById/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable int id) {
        try {
            Equipment equipment = equipmentService.getEquipmentById(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", equipment));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 创建装备
    @PostMapping("/createEquipment")
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        try {
            equipment.setCreateTime(LocalDateTime.now());
            equipment.setUpdateTime(LocalDateTime.now());
            equipmentService.createEquipment(equipment);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 更新装备
    @PostMapping("/updateEquipment")
    public ResponseEntity<?> updateEquipment(@RequestBody Equipment equipment) {
        try {
            equipment.setUpdateTime(LocalDateTime.now());
            equipmentService.updateEquipment(equipment);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

    // 删除装备
    @GetMapping("/deleteEquipment/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        try {
            equipmentService.deleteEquipment(id);
            return ResponseEntity.status(200).body(new ApiResponse<>(0, "Success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(-1, "Error", e.getMessage()));
        }
    }

}
