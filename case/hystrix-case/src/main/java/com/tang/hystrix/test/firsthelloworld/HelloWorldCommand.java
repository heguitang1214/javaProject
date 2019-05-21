package com.tang.hystrix.test.firsthelloworld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 无双老师
 * @Date: 2018/11/10
 * @Description: Hystrix hello world入门 使用命令模式封装依赖逻辑
 */
public class HelloWorldCommand extends HystrixCommand<String> {
    private final String name;

    public HelloWorldCommand(String name) {
        //最少配置:指定命令组名(CommandGroup)
        super(HystrixCommandGroupKey.Factory.asKey("HelloWorldCommandGroup"));
        this.name = name;
    }

    /**
     * 业务逻辑执行处
     *
     * @return
     */
    @Override
    protected String run() {
        // 依赖逻辑封装在run()方法中 如：远程调用
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }

    /**
     * 调用实例
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //每个Command对象只能调用一次,不可以重复调用,
        //重复调用对应异常信息:This instance can only be executed once. Please instantiate a new instance.
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
        //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        String result = helloWorldCommand.execute();
        System.out.printf("result=%s%n", result);
        System.out.println("--------------------------分割线------------------------------");
        helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
        //异步调用,可自由控制获取结果时机
        //Future类似银行存款的存根，持有存根可以随时获取存款的本息
        Future<String> future = helloWorldCommand.queue();
        //get操作超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        System.out.printf("result=%s%n", result);
    }

}
