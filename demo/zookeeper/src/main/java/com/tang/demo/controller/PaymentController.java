package com.tang.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService service;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        Payment p = service.getById();
        return "OrderId: " + p.getOrderId() + ", Price: " + p.getPayPrice();
    }
}
