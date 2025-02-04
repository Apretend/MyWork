package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.EquipmentMapper;
import com.lyh.storyapi.model.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    // 获取全部装备
    public List<Equipment> getEquipment()
    {
        return equipmentMapper.selectList(null);
    }

    // 通过ID获取装备
    public Equipment getEquipmentById(int id)
    {
        return equipmentMapper.selectById(id);
    }

    // 通过书ID获取装备
    public List<Equipment> getEquipmentsByBookId(int bookId)
    {
        return equipmentMapper.getEquipmentsByBookId(bookId);
    }

    // 创建装备
    public void createEquipment(Equipment equipment)
    {
        equipmentMapper.insert(equipment);
    }

    // 更新装备
    public void updateEquipment(Equipment equipment)
    {
        equipmentMapper.updateById(equipment);
    }

    // 删除装备
    public void deleteEquipment(int id)
    {
        equipmentMapper.deleteById(id);
    }

}
