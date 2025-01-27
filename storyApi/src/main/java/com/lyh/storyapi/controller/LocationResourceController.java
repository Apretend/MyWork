package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.LocationResource;
import com.lyh.storyapi.service.LocationResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/Location")
public class LocationResourceController {

    @Autowired
    private LocationResourceService locationResourceService;

    // 获取所有地点
    @GetMapping("/getAllLocationResource")
    public ResponseEntity<?> getAllLocationResource() {
        try {
            List<LocationResource> locationResourceList = locationResourceService.getAllLocationResource();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",locationResourceList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 通过id获取地点
    @GetMapping("/getLocationResourceById/{id}")
    public ResponseEntity<?> getLocationResourceById(@PathVariable int id) {
        try {
            LocationResource locationResource = locationResourceService.getLocationResourceById(id);
            if (locationResource != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",locationResource));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"Error",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 创建地点
    @PostMapping("/createLocation")
    public ResponseEntity<?> createLocationResource(@RequestBody LocationResource locationResource) {
        try {
            locationResource.setCreateTime(LocalDateTime.now());
            locationResource.setUpdateTime(LocalDateTime.now());
            locationResourceService.createLocationResource(locationResource);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 更新地点
    @PostMapping("/updateLocation")
    public ResponseEntity<?> updateLocationResource(@RequestBody LocationResource locationResource) {
        try {
            locationResource.setUpdateTime(LocalDateTime.now());
            locationResourceService.updateLocationResource(locationResource);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 删除地点
    @GetMapping("/deleteLocation/{id}")
    public ResponseEntity<?> deleteLocationResource(@PathVariable int id) {
        try {
            locationResourceService.deleteLocationResource(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

}

