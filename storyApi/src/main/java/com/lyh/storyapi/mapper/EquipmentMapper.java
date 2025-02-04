package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {

    @Select("SELECT * FROM equipment WHERE book_id = #{BookId}")
    List<Equipment> getEquipmentsByBookId(int BookId);

}
