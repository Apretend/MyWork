package com.lyh.storyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyh.storyapi.model.Weapon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeaponMapper extends BaseMapper<Weapon> {

    @Select("SELECT * FROM weapon WHERE weapon_name = #{weaponName}")
    Weapon getWeaponByWeaponName(String weaponName);

    @Select("SELECT * FROM weapon WHERE book_id = #{bookId}")
    List<Weapon> getWeaponByBookId(Integer bookId);

}
