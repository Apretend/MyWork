package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.GongFa;
import com.lyh.storyapi.service.GongFaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gongFa")
public class GongFaController {

    @Autowired
    private GongFaService gongFaService;

    // 获取所有功法
    @GetMapping("/getAllGongFa")
    public ResponseEntity<?> getAllGongFa() {
        try {
            List<GongFa> gongFaList = gongFaService.getAllGongFa();
            if (gongFaList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",gongFaList));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"Error",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 根据id获取功法
    @GetMapping("/getGongFaById/{id}")
    public ResponseEntity<?> getGongFaById(@PathVariable int id) {
        try {
            GongFa gongFa = gongFaService.getGongFaById(id);
            if (gongFa != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",gongFa));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"Error",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 创建功法
    @PostMapping("/createGongFa")
    public ResponseEntity<?> createGongFa(@RequestBody GongFa gongFa) {
        try {
            gongFa.setCreateTime(LocalDateTime.now());
            gongFa.setUpdateTime(LocalDateTime.now());
            gongFaService.createGongFa(gongFa);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 更新功法
    @PostMapping("/updateGongFa")
    public ResponseEntity<?> updateGongFa(@RequestBody GongFa gongFa) {
        try {
            gongFa.setUpdateTime(LocalDateTime.now());
            gongFaService.updateGongFa(gongFa);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 删除功法
    @GetMapping("/deleteGongFa/{id}")
    public ResponseEntity<?> deleteGongFa(@PathVariable int id) {
        try {
            gongFaService.deleteGongFa(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

}
