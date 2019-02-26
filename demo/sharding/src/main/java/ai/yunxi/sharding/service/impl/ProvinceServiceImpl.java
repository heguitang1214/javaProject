package ai.yunxi.sharding.service.impl;

import ai.yunxi.sharding.mapper.ProvinceMapper;
import ai.yunxi.sharding.model.Province;
import ai.yunxi.sharding.service.ProvinceService;
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
