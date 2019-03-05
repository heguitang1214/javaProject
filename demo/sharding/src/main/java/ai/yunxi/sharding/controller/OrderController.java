package ai.yunxi.sharding.controller;

import ai.yunxi.sharding.model.ItemGenerator;
import ai.yunxi.sharding.model.Order;
import ai.yunxi.sharding.model.OrderGenerator;
import ai.yunxi.sharding.model.OrderItem;
import ai.yunxi.sharding.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author heguitang
 * @Date 2019/3/5 15:34
 * @Version 1.0
 * @Desc 订单
 */
@Controller
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @RequestMapping("/saveOrder")
    @ResponseBody
    public String saveOrder(){
        Order order = OrderGenerator.generate();
        order.setUserId(10000000);
        order.setOrderId(1000000);
        OrderItem orderItem = ItemGenerator.generate();
        orderItem.setUserId(order.getUserId());
        orderItem.setOrderId(order.getOrderId());
        orderService.save(order, orderItem);
        return "订单保存成功！";
    }


}
