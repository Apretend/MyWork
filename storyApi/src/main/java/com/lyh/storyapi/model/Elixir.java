package com.lyh.storyapi.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("elixir")
public class Elixir {

    @TableId(value = "elixir_id", type = IdType.AUTO)
    private int elixirId;

    @TableField("book_id")
    private int bookId;

    @TableField("elixir_name")
    private String elixirName;

    @TableField("elixir_type")
    private String elixirType;

    @TableField("elixir_effect")
    private String elixirEffect;

    @TableField("elixir_origin")
    private String elixirOrigin;

    @TableField("elixir_key_ingredient")
    private String elixirKeyIngredient;

    @TableField("elixir_description")
    private String elixirDescription;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

}
