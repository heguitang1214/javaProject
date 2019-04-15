package com.tang.controller;

import com.tang.entry.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by heguitang on 2018/10/3.
 * Spring测试
 */
@RestController
public class TestController {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Person person;

    @RequestMapping("/sayhello")
    public String sayHello() {
        System.out.println("JDBC template:" + template);
        return "hello SpringBoot......" + person.getName() + "年龄:" + person.getAge();
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}
