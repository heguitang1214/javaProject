package com.zero.product.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.zero.core.entity.Order;

/**
 * 
 * @author heguiatng
 * @createTime 2018年7月24日 上午11:48:27 
 * 修改产品接口
 */
@Mapper
public interface ProductMapper {
	int update(Order order);
}
