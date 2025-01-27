package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

    @Select("SELECT * FROM resource WHERE resource_name = #{ResourceName}")
    Resource getResourceByName(String ResourceName);

    @Select("SELECT * FROM resource WHERE is_global = #{isGlobal}")
    List<Resource> getResourceByGlobal(int isGlobal);
}
