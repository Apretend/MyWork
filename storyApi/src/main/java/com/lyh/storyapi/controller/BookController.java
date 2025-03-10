package com.lyh.storyapi.controller;

import com.lyh.storyapi.dto.ApiResponse;
import com.lyh.storyapi.dto.BookAndAuthorId;
import com.lyh.storyapi.mapper.BookMapper;
import com.lyh.storyapi.model.Book;
import com.lyh.storyapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;

    private static final String UPLOAD_DIR = "D:\\upload";

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

    // 根据书名模糊查询
    @PostMapping("/getBookByName")
    public ResponseEntity<?> getBookByName(@RequestBody BookAndAuthorId bookAndAuthorId) {
        try {
            List<Book> bookList = bookService.getBookListByNameAndUserId(bookAndAuthorId.getBookName(),bookAndAuthorId.getAuthorId());
            if (bookList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"success",bookList));
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(1,"fail",null));
           }
        } catch (Exception e) {
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

    // 上传图片
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file")MultipartFile file) {
        try {
            if(file.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(-11,"文件为空",null));
            }
            // 文件名
            String fileName = file.getOriginalFilename();
            // 构建目标文件路径
            String filePath = UPLOAD_DIR + fileName;
            File targetFile = new File(filePath);
            targetFile.getParentFile().mkdirs();
            file.transferTo(targetFile);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"上传成功",filePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(-1,"上传失败",e.getMessage()));
        }
    }

    @PostMapping("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        try {
            int affectedRows = bookMapper.updateBookSelective(book);
            if (affectedRows == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(1,"更新失败",null));
            }else{
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(0,"更新成功",null));
            }
        }catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(-1,"更新失败",e.getMessage()));
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

