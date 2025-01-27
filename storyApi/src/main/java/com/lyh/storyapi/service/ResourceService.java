package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.ResourceMapper;
import com.lyh.storyapi.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    // 获取全部资源
    public List<Resource> getAllResource()
    {
        return resourceMapper.selectList(null);
    }

    // 根据资源名称获取资源
    public Resource getResourceByName(String ResourceName)
    {
        return resourceMapper.getResourceByName(ResourceName);
    }

    // 根据资源id获取资源
    public Resource getResourceById(int id)
    {
        return resourceMapper.selectById(id);
    }

    // 根据全局字段获取资源
    public List<Resource> getResourceByGlobal(int isGlobal)
    {
        return resourceMapper.getResourceByGlobal(isGlobal);
    }

    // 创建资源
    public void createResource(Resource resource)
    {
        resourceMapper.insert(resource);
    }

    // 更新资源
    public void updateResource(Resource resource)
    {
        resourceMapper.updateById(resource);
    }

    // 删除资源
    public void deleteResource(int id)
    {
        resourceMapper.deleteById(id);
    }


}
