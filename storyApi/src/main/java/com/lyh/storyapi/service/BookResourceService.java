package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.BookResourceMapper;
import com.lyh.storyapi.model.BookResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookResourceService {

    @Autowired
    private BookResourceMapper bookResourceMapper;

    // 获取所有书籍资源
    public List<BookResource> getBookResource()
    {
        return bookResourceMapper.selectList(null);
    }

    // 通过id获取书籍资源
    public BookResource getBookResourceById(int id)
    {
        return bookResourceMapper.selectById(id);
    }

    // 创建书籍资源
    public void createBookResource(BookResource bookResource)
    {
        bookResourceMapper.insert(bookResource);
    }

    // 更新书籍资源
    public void updateBookResource(BookResource bookResource)
    {
        bookResourceMapper.updateById(bookResource);
    }

    // 删除书籍资源
    public void deleteBookResource(int id)
    {
        bookResourceMapper.deleteById(id);
    }

}
