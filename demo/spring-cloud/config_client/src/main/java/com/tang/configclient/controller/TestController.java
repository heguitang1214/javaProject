package com.tang.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  测试获取spring cloud配置文件
 */
@Controller
@RefreshScope//暴露refersh方法
public class TestController {

    @Value("${redis.username}")
    private String name;

    @RequestMapping("test")
    @ResponseBody
    public String getConfig(){
        return "当前的 username 配置文件 是 "+name;
    }

}
