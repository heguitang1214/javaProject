package com.zero.rpc;

import java.net.InetSocketAddress;

public class RPCTest {
    public static void main(String[] args) {
        DemoService service = RPCClient.remoteCall(DemoService.class,
            new InetSocketAddress("localhost", 8080));
        System.out.println(service.sayHello("qingtian"));
    }
}
