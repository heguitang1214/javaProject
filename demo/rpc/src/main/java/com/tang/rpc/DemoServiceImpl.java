package com.tang.rpc;

/**
 * RPC服务的具体实现
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "RPC服务具体的实现类DemoServiceImpl,直接返回请求参数：" + name;
    }
}
