package com.tang.springcloudconfigclientdemo;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;

public class MyHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // 自定义监控检查的意义在于可以自定义一些系统监控指标检查机制，
        // 或者说业务监控指标
        System.out.println("myself check health");
        builder.down().withDetail("myself check health", "just so so");
    }

    @Bean
    public HealthIndicator healthIndicator() {
        return new MyHealthIndicator();
    }
}

