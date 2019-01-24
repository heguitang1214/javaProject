package com.zero.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestController
 * @Description TODO
 * @Author 云析-路飞
 * @Date 2018/5/9 16:12
 * @Version 1.0
 */
@Controller
@RefreshScope
public class TestController {

    @Value("${redis.username}")
    private String name;

    @RequestMapping("test")
    @ResponseBody
    public String getConfig(){
        return "当前的 username 配置文件 是 "+name;
    }

}
