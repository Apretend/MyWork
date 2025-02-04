package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("equipment")
public class Equipment {

    @TableId(value = "equipment_id", type = IdType.AUTO)
    private int equipmentId;

    @TableField("book_id")
    private int bookId;

    @TableField("equipment_name")
    private String equipmentName;

    @TableField("equipment_type")
    private String equipmentType;

    @TableField("equipment_owner")
    private String equipmentOwner;

    @TableField("equipment_effect")
    private String equipmentEffect;

    @TableField("equipment_origin")
    private String equipmentOrigin;

    @TableField("equipment_description")
    private String equipmentDescription;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;
}
