package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("book_details")
public class Book {

    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    @TableField("book_name")
    private String bookName;

    @TableField("alternate_name_1")
    private String alternateName1;

    @TableField("alternate_name_2")
    private String alternateName2;

    @TableField("alternate_name_3")
    private String alternateName3;

    @TableField("alternate_name_4")
    private String alternateName4;

    @TableField(value = "description")
    private String description;

    @TableField("cover_link")
    private String coverLink;

    @TableField("platform_id")
    private Integer platformId;

    @TableField("is_published")
    private Integer isPublished = 0;

    @TableField("user_id")
    private Integer userId;

    @TableField(value = "book_content_description")
    private String bookContentDescription;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
