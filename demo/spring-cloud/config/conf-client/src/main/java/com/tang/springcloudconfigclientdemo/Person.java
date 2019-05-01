package com.tang.springcloudconfigclientdemo;

        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.cloud.context.config.annotation.RefreshScope;
        import org.springframework.stereotype.Component;

/**
 * 配置Bean
 *
 * @author : Five-云析学院
 * @since : 2019年04月19日 21:31
 */

@Component
@RefreshScope
public class Person {
    @Value("${name}")
    private String name;

    public String getName() {
        return name;
    }
}
