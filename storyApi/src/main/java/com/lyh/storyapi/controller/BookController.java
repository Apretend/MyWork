package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.model.Book;
import com.lyh.storyapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/getBooks")
    public ResponseEntity<?> getBooks() {
        try {
            List<Book> bookList = bookService.getBookList();
            if (bookList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"success",bookList));
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"fail",null));
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(-1,"fail",e.getMessage()));
        }
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            Book book = bookService.getBookById(id);
            if (book != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"success",book));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"fail",null));
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(-1,"fail",e.getMessage()));
        }
    }

    @PostMapping("/createBook")
    public ResponseEntity<?> createBook(@RequestBody Book book) {
       try {
           book.setCreateTime(LocalDateTime.now());
           book.setUpdateTime(LocalDateTime.now());
           bookService.createBook(book);
           return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(0, "新建成功",book));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1, "小说创建失败",e.getMessage()));
       }
    }

    @PostMapping("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        System.out.println(book);
        try {
            Book existingBook = bookService.getBookById(book.getBookId());
            if (existingBook == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(1,"小说未找到",null));
            }
            if (book.getBookName() == null ||
                book.getBookContentDescription() == null ||
                book.getDescription() == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(1,"小说书名，简介，大纲不可为空",null));
            }
            existingBook.setBookId(book.getBookId());
            existingBook.setBookName(book.getBookName());
            existingBook.setBookContentDescription(book.getBookContentDescription());
            existingBook.setDescription(book.getDescription());
            existingBook.setCoverLink(book.getCoverLink());
            existingBook.setAlternateName1(book.getAlternateName1());
            existingBook.setAlternateName2(book.getAlternateName2());
            existingBook.setAlternateName3(book.getAlternateName3());
            existingBook.setAlternateName4(book.getAlternateName4());
            existingBook.setUserId(book.getUserId());
            existingBook.setPlatformId(book.getPlatformId());
            existingBook.setIsPublished(book.getIsPublished());
            existingBook.setUpdateTime(LocalDateTime.now());
            bookService.updateBook(book);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(0,"更新成功",book));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1,"更新失败",e.getMessage()));
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(0,"删除成功",null));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-1,"删除失败",e.getMessage()));
        }
    }

}
