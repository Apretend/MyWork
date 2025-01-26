package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
