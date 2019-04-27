package com.tang.springcloudconfigserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config配置中心启动类 加上@EnableConfigServer注解
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigServerDemoApplication.class, args);
	}

}
