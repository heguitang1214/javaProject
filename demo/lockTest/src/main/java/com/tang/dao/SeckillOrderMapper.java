package com.tang.dao;

import java.util.List;

import com.tang.pojo.SeckillOrder;
import com.tang.pojo.SeckillOrderExample;
import org.apache.ibatis.annotations.Param;

public interface SeckillOrderMapper {
    long countByExample(SeckillOrderExample example);

    int deleteByExample(SeckillOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SeckillOrder record);

    int insertSelective(SeckillOrder record);

    List<SeckillOrder> selectByExample(SeckillOrderExample example);

    SeckillOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SeckillOrder record, @Param("example") SeckillOrderExample example);

    int updateByExample(@Param("record") SeckillOrder record, @Param("example") SeckillOrderExample example);

    int updateByPrimaryKeySelective(SeckillOrder record);

    int updateByPrimaryKey(SeckillOrder record);
}