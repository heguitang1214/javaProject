package com.tang.demo.registry;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		HelloService service = ServiceInvoke.remoteCall(HelloService.class);
		System.out.println(service.hello("qingtian"));
	}
}
