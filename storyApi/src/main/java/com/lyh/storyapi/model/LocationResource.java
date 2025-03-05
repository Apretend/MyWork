package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("location_resource")
public class LocationResource {

    @TableId(value = "location_id", type = IdType.AUTO)
    private Integer locationId;

    @TableField("book_id")
    private Integer bookId;

    @TableField("location_name")
    private String locationName;

    @TableField("location_type")
    private String locationType;

    @TableField("location_feature")
    private String locationFeature;

    @TableField("location_position")
    private String locationPosition;

    @TableField("location_description")
    private String locationDescription;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
