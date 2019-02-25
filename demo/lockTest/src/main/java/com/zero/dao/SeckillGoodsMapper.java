package com.zero.dao;

import com.zero.pojo.SeckillGoods;
import com.zero.pojo.SeckillGoodsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeckillGoodsMapper {
    long countByExample(SeckillGoodsExample example);

    int deleteByExample(SeckillGoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SeckillGoods record);

    int insertSelective(SeckillGoods record);

    List<SeckillGoods> selectByExample(SeckillGoodsExample example);

    SeckillGoods selectByPrimaryKey(Integer id);
    //悲观锁查询
    SeckillGoods selectByPrimaryKeyWithPessimistic(Integer id);

    int updateByExampleSelective(@Param("record") SeckillGoods record, @Param("id") Integer id, @Param("version") Integer version);

    int updateByNoLock(@Param("record") SeckillGoods record, @Param("id") Integer id);

    int updateByExample(@Param("record") SeckillGoods record, @Param("example") SeckillGoodsExample example);

    int updateByPrimaryKeySelective(SeckillGoods record);

    int updateByPrimaryKey(SeckillGoods record);
}