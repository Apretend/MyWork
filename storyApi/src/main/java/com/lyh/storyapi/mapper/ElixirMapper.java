package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Elixir;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ElixirMapper extends BaseMapper<Elixir> {

    @Select("SELECT * FROM elixir WHERE book_id = #{BookId}")
    List<Elixir> getElixirsByBookId(int BookId);

}
