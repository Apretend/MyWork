package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.GongFaMapper;
import com.lyh.storyapi.model.GongFa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GongFaService {

    @Autowired
    private GongFaMapper gongFaMapper;

    // 获取所有功法
    public List<GongFa> getAllGongFa() {
        return gongFaMapper.selectList(null);
    }

    // 根据id获取功法
    public GongFa getGongFaById(int id) {
        return gongFaMapper.selectById(id);
    }

    // 创建功法
    public void createGongFa(GongFa gongFa) {
        gongFaMapper.insert(gongFa);
    }

    // 更新功法
    public void updateGongFa(GongFa gongFa) {
        gongFaMapper.updateById(gongFa);
    }

    // 删除功法
    public void deleteGongFa(int id) {
        gongFaMapper.deleteById(id);
    }

}
