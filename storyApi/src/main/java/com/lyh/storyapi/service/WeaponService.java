package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.WeaponMapper;
import com.lyh.storyapi.model.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponService {

    @Autowired
    private WeaponMapper weaponMapper;

    // 获取所有武器
    public List<Weapon> getAllWeapons() {
        return weaponMapper.selectList(null);
    }

    // 获取指定书下的所有武器
    public List<Weapon> getAllWeaponByBookId(int bookId) {
        return weaponMapper.getWeaponByBookId(bookId);
    }

    // 通过武器ID获取武器
    public Weapon getWeaponById(int id) {
        return weaponMapper.selectById(id);
    }

    // 新建武器
    public void createWeapon(Weapon weapon) {
        weaponMapper.insert(weapon);
    }

    // 修改武器
    public void updateWeapon(Weapon weapon) {
        weaponMapper.updateById(weapon);
    }

    // 删除武器
    public void deleteWeapon(int id) {
        weaponMapper.deleteById(id);
    }

}
