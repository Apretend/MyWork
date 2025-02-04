package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("character_resource")
public class CharacterResource {

    @TableId(value = "character_id")
    private int characterId;

    @TableField("book_id")
    private int bookId;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("age")
    private int age;

    @TableField("personality")
    private String personality;

    @TableField("character_description")
    private String characterDescription;

    @TableField("strength")
    private String strength;

    @TableField("is_alive")
    private int isAlive;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
