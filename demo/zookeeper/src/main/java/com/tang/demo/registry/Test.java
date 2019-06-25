package com.tang.demo.registry;

import java.io.IOException;

/**
 * 服务的调用：RPC调用
 */
public class Test {

    public static void main(String[] args) throws IOException {
        HelloService service = ServiceInvoke.remoteCall(HelloService.class);
        System.out.println(service.hello("qingtian"));
    }
}
