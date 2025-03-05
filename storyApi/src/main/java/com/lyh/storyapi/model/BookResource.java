package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("book_resource")
public class BookResource {

    @TableId(value = "book_id", type = IdType.AUTO)
    private Integer bookId;

    @TableField("resource_id")
    private Integer resourceId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
