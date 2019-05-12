package com.tang.sharding.mapper;

import com.tang.sharding.model.Province;
import org.springframework.stereotype.Component;

@Component
public interface ProvinceMapper {
    public int save(Province province);
}
