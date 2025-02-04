package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.CharacterResourceMapper;
import com.lyh.storyapi.model.CharacterResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterResourceService {

    @Autowired
    private CharacterResourceMapper characterResourceMapper;

    // 获取所有人物
    public List<CharacterResource> getCharacterResource() {
        return characterResourceMapper.selectList(null);
    }

    // 通过id获取人物
    public CharacterResource getCharacterResourceById(int id) {
        return characterResourceMapper.selectById(id);
    }

    // 通过bookId获取人物
    public List<CharacterResource> getCharacterResourceByBookId(int bookId) {
        return characterResourceMapper.getCharacterResourceByBookId(bookId);
    }

    // 创建人物
    public void createCharacterResource(CharacterResource characterResource) {
        characterResourceMapper.insert(characterResource);
    }

    // 更新人物
    public void updateCharacterResource(CharacterResource characterResource) {
        characterResourceMapper.updateById(characterResource);
    }

    // 删除人物
    public void deleteCharacterResource(int id) {
        characterResourceMapper.deleteById(id);
    }

}
