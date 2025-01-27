package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("weapon")
public class Weapon {

    @TableId(value = "weapon_id", type = IdType.AUTO)
    private int weaponId;   // 武器ID

    @TableField("book_id")
    private Integer bookId;  // 书ID

    @TableField("weapon_name")
    private String weaponName;  // 武器名称

    @TableField("weapon_type")
    private String weaponType;  // 武器类型

    @TableField("weapon_owner")
    private String weaponOwner;  // 武器主人

    @TableField("weapon_power")
    private String weaponPower;  // 武器威力

    @TableField("weapon_origin")
    private String weaponOrigin;  // 武器来源

    @TableField("weapon_description")
    private String weaponDescription;  // 武器描述

    @TableField(value = "create_time")
    private LocalDateTime createTime;  // 创建时间

    @TableField(value = "update_time")
    private LocalDateTime updateTime;  // 更新时间

}
