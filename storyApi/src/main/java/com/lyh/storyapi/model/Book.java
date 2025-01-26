package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book_details")
public class Book {

    @TableId(value = "book_id", type = IdType.AUTO)
    private int bookId;

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
    private int platformId;

    @TableField("is_published")
    private int isPublished = 0;

    @TableField("user_id")
    private int userId;

    @TableField(value = "book_content_description")
    private String bookContentDescription;
}
