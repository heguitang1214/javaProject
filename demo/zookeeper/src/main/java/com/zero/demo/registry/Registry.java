package com.zero.demo.registry;

import java.net.InetSocketAddress;

public interface Registry {

	public static final String ROOT = "/ZK_Services";

	public boolean register(InetSocketAddress addr, String serviceName);

}
