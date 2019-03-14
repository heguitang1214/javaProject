package com.yunxi.yunxiruleengine.template;


import com.yunxi.yunxiruleengine.entity.Order;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-17
 * @Description:
 */
public interface Rule {
    Order execute(Order order);
}
