package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.UserInfoMapper;
import com.lyh.storyapi.model.Book;
import com.lyh.storyapi.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public List<UserInfo> getAllUser() {
        return userInfoMapper.selectList(null);
    }

    public UserInfo getUserInfoById(int id) {
        return userInfoMapper.selectById(id);
    }

    public UserInfo getUserInfoByName(String name) {
        return userInfoMapper.getUserInfoByUserName(name);
    }

    public void addUser(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    public void updateUser(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
    }

    public void deleteUserByName(String name) {
        var userId = userInfoMapper.getUserInfoByUserName(name).getId();
        userInfoMapper.deleteById(userId);
    }

    public void deleteUserById(int id) {
        userInfoMapper.deleteById(id);
    }

}
