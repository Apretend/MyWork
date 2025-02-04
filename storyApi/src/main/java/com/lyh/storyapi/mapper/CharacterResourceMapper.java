package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.CharacterResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CharacterResourceMapper extends BaseMapper<CharacterResource> {

    @Select("SELECT * FROM character_resource WHERE book_id = #{BookId}")
    List<CharacterResource> getCharacterResourceByBookId(int BookId);

}
