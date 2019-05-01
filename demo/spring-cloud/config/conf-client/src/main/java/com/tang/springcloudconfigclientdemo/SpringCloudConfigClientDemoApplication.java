package com.tang.springcloudconfigclientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudConfigClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientDemoApplication.class, args);
    }

    @Bean
    public HealthIndicator healthIndicator() {
        return new MyHealthIndicator();
    }

}
