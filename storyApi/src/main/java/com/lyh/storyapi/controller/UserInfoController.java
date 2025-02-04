package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.UserInfo;
import com.lyh.storyapi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    // 登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfo user) {
        try {
            UserInfo userInfo = userInfoService.getUserInfoByName(user.getUsername());
            if (userInfo != null) {
                if (userInfo.getPassword().equals(user.getPassword())) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",userInfo));
                }else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"Error","密码错误"));
                }
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"Error","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(-1,"Error",e.getMessage()));
        }
    }

    // 获取所有用户
    @GetMapping("/getAllUser")
    public ResponseEntity<?> getAllUser() {
        try {
            List<UserInfo> userList = userInfoService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",userList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "获取用户列表失败",e.getMessage()));
        }
    }

    // 通过用户名搜索用户
    @GetMapping("/getUserByName/{name}")
    public ResponseEntity<?> getUserByName(@PathVariable("name") String name) {
        try {
            UserInfo user = userInfoService.getUserInfoByName(name);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",user));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"Error","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "获取用户失败",e.getMessage()));
        }
    }

    // 通过ID搜索用户
    @PostMapping("/getUserById")
    public ResponseEntity<?> getUserById(@RequestBody UserInfo user) {
        try {
            UserInfo userInfo = userInfoService.getUserInfoById(user.getId());
            if (userInfo != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",userInfo));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(1,"Error","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "获取用户失败",e.getMessage()));
        }
    }

    // 创建用户
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserInfo user) {
        try {
            UserInfo existUser = userInfoService.getUserInfoByName(user.getUsername());
            if (existUser != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(2,"Error","用户名已存在"));
            }
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userInfoService.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "创建用户失败",e.getMessage()));
        }
    }

    // 修改用户
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserInfo user) {
        try {
            UserInfo existUser = userInfoService.getUserInfoById(user.getId());
            if (existUser != null) {
                existUser.setUsername(user.getUsername());
                existUser.setUpdateTime(LocalDateTime.now());
                existUser.setPassword(user.getPassword());
                existUser.setVipCode(user.getVipCode());
                userInfoService.updateUser(existUser);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",user));
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(1, "创建用户失败","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "修改用户失败",e.getMessage()));
        }
    }

    // 删除用户
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        try {
            UserInfo existUser = userInfoService.getUserInfoById(id);
            if (existUser != null) {
                userInfoService.deleteUserById(id);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(0,"Success",null));
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(1, "删除用户失败","用户不存在"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "删除用户失败",e.getMessage()));
        }
    }


}
