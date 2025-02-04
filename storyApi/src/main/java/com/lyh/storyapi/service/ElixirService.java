package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.ElixirMapper;
import com.lyh.storyapi.model.Elixir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElixirService {

    @Autowired
    private ElixirMapper elixirMapper;

    // 获取所有丹药
    public List<Elixir> getElixir()
    {
        return elixirMapper.selectList(null);
    }

    // 通过ID获取丹药
    public Elixir getElixirById(int id)
    {
        return elixirMapper.selectById(id);
    }

    // 通过书ID获取丹药
    public List<Elixir> getElixirsByBookId(int bookId)
    {
        return elixirMapper.getElixirsByBookId(bookId);
    }

    // 创建丹药
    public void createElixir(Elixir elixir)
    {
        elixirMapper.insert(elixir);
    }

    // 更新丹药
    public void updateElixir(Elixir elixir)
    {
        elixirMapper.updateById(elixir);
    }

    // 删除丹药
    public void deleteElixir(int id)
    {
        elixirMapper.deleteById(id);
    }



}
