package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.DocumentMapper;
import com.lyh.storyapi.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentMapper documentMapper;

    // 获取所有文档
    public List<Document> getDocument() {
        return documentMapper.selectList(null);
    }

    // 通过文件ID获取文档
    public List<Document> getDocumentByDirectoryId(int directoryId) {
        return documentMapper.getDocumentByDirectoryId(directoryId);
    }

    // 通过文档ID获取文档
    public Document getDocumentById(int id) {
        return documentMapper.selectById(id);
    }

    // 创建文档
    public void createDocument(Document document) {
        documentMapper.insert(document);
    }

    // 更新文档
    public void updateDocument(Document document) {
        documentMapper.updateById(document);
    }

    // 删除文档
    public void deleteDocument(int id) {
        documentMapper.deleteById(id);
    }

}
