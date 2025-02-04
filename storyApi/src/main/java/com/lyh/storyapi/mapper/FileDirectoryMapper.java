package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.FileDirectory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileDirectoryMapper extends BaseMapper<FileDirectory> {

    @Select("SELECT * FROM file_directory WHERE book_id = #{BookId}")
    List<FileDirectory> getFileDirectoryByBookId(int BookId);

}
