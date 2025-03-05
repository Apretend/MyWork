package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("resource")
public class Resource {

    @TableId(value = "resource_id", type = IdType.AUTO)
    private Integer resourceId;

    @TableField("resource_name")
    private String resourceName;

    @TableField("is_global")
    private Integer isGlobal;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
