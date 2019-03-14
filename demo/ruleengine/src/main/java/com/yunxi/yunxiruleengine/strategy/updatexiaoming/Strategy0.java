package com.yunxi.yunxiruleengine.strategy.updatexiaoming;


import com.yunxi.yunxiruleengine.entity.Order;

import static com.yunxi.yunxiruleengine.xiaoming.JavaScoreExample.addScore;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-16
 * @Description:
 */
public class Strategy0 implements Strategy {
    @Override
    public void cal(Order order) {
        order.setScore(0);
        addScore(order);
    }
}
