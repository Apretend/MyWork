package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.mapper.WeaponMapper;
import com.lyh.storyapi.model.Weapon;
import com.lyh.storyapi.service.BookService;
import com.lyh.storyapi.service.WeaponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/weapon")
public class WeaponController {

    @Autowired
    private WeaponService weaponService;
    private BookService bookService;

    // 获取所有武器
    @GetMapping("/getAllWeapon")
    public ResponseEntity<?> getAllWeapon() {
        try {
            List<Weapon> weaponList = weaponService.getAllWeapons();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",weaponList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 获取指定书下的所有武器
    @PostMapping("/getAllWeaponByBookId")
    public ResponseEntity<?> getAllWeaponByBookId(int bookId) {
        try {
            List<Weapon> weaponList = weaponService.getAllWeaponByBookId(bookId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",weaponList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 获取指定武器
    @PostMapping("/getWeaponById")
    public ResponseEntity<?> getWeaponById(int id) {
        try {
            Weapon weapon = weaponService.getWeaponById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",weapon));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 创建武器
    @PostMapping("/createWeapon")
    public ResponseEntity<?> createWeapon(@RequestBody Weapon weapon) {
        try {
            weapon.setCreateTime(LocalDateTime.now());
            weapon.setUpdateTime(LocalDateTime.now());
            weaponService.createWeapon(weapon);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",weapon));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 更新武器
    @PostMapping("/updateWeapon")
    public ResponseEntity<?> updateWeapon(@RequestBody Weapon weapon) {
        try {
            weapon.setUpdateTime(LocalDateTime.now());
            weaponService.updateWeapon(weapon);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",weapon));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 删除武器
    @PostMapping("/deleteWeapon")
    public ResponseEntity<?> deleteWeapon(int id) {
        try {
            weaponService.deleteWeapon(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }
}
