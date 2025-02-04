package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.FileDirectoryMapper;
import com.lyh.storyapi.model.FileDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDirectoryService {

    @Autowired
    private FileDirectoryMapper fileDirectoryMapper;

    // 获取文件目录
    public List<FileDirectory> getFileDirectory() {
        return fileDirectoryMapper.selectList(null);
    }

    // 通过id获取文件目录
    public FileDirectory getFileDirectoryById(int id) {
        return fileDirectoryMapper.selectById(id);
    }

    // 通过bookId获取目录
    public List<FileDirectory> getFileDirectoryByBookId(int bookId) {
        return fileDirectoryMapper.getFileDirectoryByBookId(bookId);
    }

    // 添加文件目录
    public void addFileDirectory(FileDirectory fileDirectory) {
        fileDirectoryMapper.insert(fileDirectory);
    }

    // 更新文件目录
    public void updateFileDirectory(FileDirectory fileDirectory) {
        fileDirectoryMapper.updateById(fileDirectory);
    }

    // 删除文件目录
    public void deleteFileDirectory(int id) {
        fileDirectoryMapper.deleteById(id);
    }

}
