package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
public class UserInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "user_name")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "vip_code")
    private String vipCode;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}
