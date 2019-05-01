package com.tang.springcloudconfigclientdemo;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MySpringApplicationEvent {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //添加监听器
        ApplicationListener<MyApplicationEvent> listener = new ApplicationListener<MyApplicationEvent>() {
            @Override
            public void onApplicationEvent(MyApplicationEvent event) {
                System.out.println("Spring监听到事件：" + event.getSource());
            }
        };
        context.addApplicationListener(listener);

        //调用刷新
        context.refresh();
        context.publishEvent(new MyApplicationEvent("Hello"));
        context.publishEvent(new MyApplicationEvent(1));
    }

    private static class MyApplicationEvent extends ApplicationEvent {
        public MyApplicationEvent(Object source) {
            super(source);
        }
    }
}