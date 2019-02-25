package com.zero.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zero.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ClassName OrderController
 * @Description TODO
 * @Author 云析-路飞
 * @Date 2018/5/5 15:42
 * @Version 1.0
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * @param
     * @return java.util.List<ActivemqRestaurant>
     * @method 方法描述:通过无锁方式抢购
     * @author 云析-路飞
     * @date 14:45
     */
    @RequestMapping("/nolock")
    @ResponseBody
    public void nolock() throws Exception {

        orderService.seckillWithNoLock();
    }


    /**
     * @param
     * @return java.util.List<ActivemqRestaurant>
     * @method 方法描述:通过乐观锁抢购,无重试
     * @author 云析-路飞
     * @date 14:45
     */
    @RequestMapping("/optimistic")
    @ResponseBody
    public void optimistic() throws Exception {

        orderService.seckillWithOptimistic();


    }

    /**
     * @param
     * @return java.util.List<ActivemqRestaurant>
     * @method 方法描述:通过乐观锁实现抢购，有重试
     * @author 云析-路飞
     * @date 14:45
     */
    @RequestMapping("/optimisticWithRetry")
    @ResponseBody
    public void optimisticWithRetry() throws Exception {

        // orderService.seckillWithOptimistic();

        while (true) {
            int i = orderService.seckillWithOptimistic();
            //如果卖光了 或者卖出成功跳出循环，否则一直循环 直到卖出去为止
            if (i == -1 || i > 0)
                break;

        }

    }


    /**
     * @param
     * @return java.util.List<ActivemqRestaurant>
     * @method 方法描述:通过悲观锁抢购
     * @author 云析-路飞
     * @date 14:45
     */
    @RequestMapping("/pessimistic")
    @ResponseBody
    public void pessimistic() throws Exception {

        orderService.seckillWithPessimistic();
    }


    /**
     * @param
     * @return java.util.List<ActivemqRestaurant>
     * @method 方法描述:通过redis原子操作
     * @author 云析-路飞
     * @date 14:45
     */
    @RequestMapping("/byRedis")
    @ResponseBody
    public void byRedis() throws Exception {

        orderService.seckillWithRedis();
    }


    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
        RateLimiter rateLimiter = RateLimiter.create(2);

        while (true) {
            rateLimiter.acquire(1);
            System.out.println(simpleDateFormat.format(new Date()));
        }
    }


}
