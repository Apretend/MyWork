package com.lyh.storyapi.service;

import com.lyh.storyapi.mapper.LocationResourceMapper;
import com.lyh.storyapi.model.LocationResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationResourceService {

    @Autowired
    private LocationResourceMapper locationResourceMapper;

    // 获取所有地点资源
    public List<LocationResource> getAllLocationResource() {
        return locationResourceMapper.selectList(null);
    }

    // 通过地点资源id获取地点资源
    public LocationResource getLocationResourceById(int id) {
        return locationResourceMapper.selectById(id);
    }

    // 新建地点资源
    public void createLocationResource(LocationResource locationResource) {
        locationResourceMapper.insert(locationResource);
    }

    // 更新地点资源
    public void updateLocationResource(LocationResource locationResource) {
        locationResourceMapper.updateById(locationResource);
    }

    // 删除资源
    public void deleteLocationResource(int id) {
        locationResourceMapper.deleteById(id);
    }

}
