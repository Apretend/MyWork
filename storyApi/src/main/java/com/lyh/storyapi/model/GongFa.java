package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("gong_fa")
public class GongFa {

    @TableId(value = "technique_id", type = IdType.AUTO)
    private Integer techniqueId;

    @TableField("book_id")
    private Integer bookId;

    @TableField("technique_name")
    private String techniqueName;

    @TableField("technique_type")
    private String techniqueType;

    @TableField("technique_owner")
    private String techniqueOwner;

    @TableField("technique_effect")
    private String techniqueEffect;

    @TableField("technique_origin")
    private String techniqueOrigin;

    @TableField("technique_description")
    private String techniqueDescription;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
