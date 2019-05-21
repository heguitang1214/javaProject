package com.tang.sharding.service.impl;

import com.tang.sharding.mapper.ProvinceMapper;
import com.tang.sharding.model.Province;
import com.tang.sharding.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public void save(Province province) {
        provinceMapper.save(province);
    }
}
