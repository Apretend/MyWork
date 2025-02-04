package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@TableName("file_directory")
public class FileDirectory {

    @TableId(value = "directory_id", type = IdType.AUTO)
    private int directoryId;

    @TableField(value = "directory_name")
    private String directoryName;

    @TableField(value = "book_id")
    private int bookId;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;

}
