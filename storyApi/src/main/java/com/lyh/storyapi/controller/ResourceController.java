package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Resource;
import com.lyh.storyapi.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ResourceList")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    // 获取所有资源
    @GetMapping("/getAllResource")
    public ResponseEntity<?> getAllResource()
    {
        try {
            List<Resource> resourceList = resourceService.getAllResource();
            if (resourceList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",resourceList));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"Error",null));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 通过ID获取资源
    @GetMapping("/getResourceById/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable int id)
    {
        try {
            Resource resource = resourceService.getResourceById(id);
            if (resource != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",resource));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"Error",null));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 通过全局获取资源
    @GetMapping("/getResourceByGlobal/{isGlobal}")
    public ResponseEntity<?> getResourceByGlobal(@PathVariable int isGlobal)
    {
        try {
            List<Resource> resourceList = resourceService.getResourceByGlobal(isGlobal);
            if (resourceList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",resourceList));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"Error",null));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 创建资源
    @PostMapping("/createResource")
    public ResponseEntity<?> createResource(@RequestBody Resource resource)
    {
        try {
            resource.setCreateTime(LocalDateTime.now());
            resource.setUpdateTime(LocalDateTime.now());
            resourceService.createResource(resource);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",resource));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 更新资源
    @PostMapping("/updateResource")
    public ResponseEntity<?> updateResource(@RequestBody Resource resource)
    {
        try {
            resource.setUpdateTime(LocalDateTime.now());
            resourceService.updateResource(resource);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",resource));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

    // 删除资源
    @GetMapping("/deleteResource/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable int id)
    {
        try {
            resourceService.deleteResource(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"Success",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"Error",e.getMessage()));
        }
    }

}
