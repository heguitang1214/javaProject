package com.zero.demo.controller;

import ai.yunxi.demo.dao.exp.ReduceStockException;
import ai.yunxi.demo.model.Order;
import ai.yunxi.demo.service.jta.OrderService;
import com.zero.demo.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public int save() {
        Order order = new Order();
        order.setProductId(1);
        order.setCustomer("Five");
        order.setNumber(1);
        log.info("==========> save()");

        try {
            orderService.createOrder(order);
            return 0;
        } catch (ReduceStockException e) {
            log.error("==========> error:" + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // @RequestMapping(value = "/saveByTxTemplate", method = RequestMethod.GET)
    // @ResponseBody
    // public int saveByTxTemplate() {
    // Order order = new Order();
    // order.setProductId(1);
    // order.setAmount(1);
    // log.info("==========> saveByTxTemplate()");
    // return orderService.createOrderByTxTemplate(order);
    // }
    //
    // @RequestMapping(value = "/saveByTxManual", method = RequestMethod.GET)
    // @ResponseBody
    // public int saveByTxManual() {
    // Order order = new Order();
    // order.setProductId(1);
    // order.setAmount(1);
    // log.info("==========> saveByTxManual()");
    // return orderService.createOrderByTxManual(order);
    // }
}
