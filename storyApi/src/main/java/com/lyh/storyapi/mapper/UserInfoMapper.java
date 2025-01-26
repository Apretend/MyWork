package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT * FROM user_info WHERE user_name = #{userName}")
    UserInfo getUserInfoByUserName(String userName);

}
