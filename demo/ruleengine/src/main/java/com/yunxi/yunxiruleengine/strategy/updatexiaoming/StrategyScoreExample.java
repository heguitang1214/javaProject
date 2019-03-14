package com.yunxi.yunxiruleengine.strategy.updatexiaoming;


import com.yunxi.yunxiruleengine.entity.Order;

import java.util.List;

import static com.yunxi.yunxiruleengine.xiaoming.JavaScoreExample.getInitData;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-02-16
 * @Description:
 */
public class StrategyScoreExample {
    public static void main(String[] args) throws Exception {
        strategyVersion();
    }

    /**
     * 策略模式
     */
    public static void strategyVersion() throws Exception {
        List<Order> orderList = getInitData();
        for (int i=0; i<orderList.size(); i++){
            Order order = orderList.get(i);
            if (order.getAmout() <= 100){
                Context context = new Context(new Strategy0());
                context.executeStrategy(order);
            }else if(order.getAmout() > 100 && order.getAmout() <= 500){
                Context context = new Context(new Strategy100());
                context.executeStrategy(order);
            }else if(order.getAmout() > 500 && order.getAmout() <= 1000){
                Context context = new Context(new Strategy500());
                context.executeStrategy(order);
            }else{
                Context context = new Context(new Strategy1000());
                context.executeStrategy(order);
            }
        }
    }
}
