package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.BookMapper;
import com.lyh.storyapi.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    public List<Book> getBookList(){
        return bookMapper.selectList(null);
    }

    // 根据书名和用户ID查询
    public List<Book> getBookListByNameAndUserId(String name, int userId){
        return bookMapper.getBooksByNameAndUserId(name, userId);
    }

    public Book getBookById(int id){
        return bookMapper.selectById(id);
    }

    public void createBook(Book book){
        bookMapper.insert(book);
    }

    public void updateBook(Book book){
        bookMapper.updateById(book);
    }

    public void deleteBookById(int id){
        bookMapper.deleteById(id);
    }

}
