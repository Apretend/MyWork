package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.UserInfo;
import com.lyh.storyapi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserInfo> userList = userInfoService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",userList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(1, "获取用户列表失败",e.getMessage()));
        }
    }

    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable("name") String name) {
        try {
            UserInfo user = userInfoService.getUserInfoByName(name);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",user));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"fail","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(1, "获取用户失败",e.getMessage()));
        }
    }


}
