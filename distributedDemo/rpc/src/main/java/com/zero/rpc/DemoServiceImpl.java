package com.zero.rpc;

/**
 * RPC服务的具体实现
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello: " + name;
    }
}
