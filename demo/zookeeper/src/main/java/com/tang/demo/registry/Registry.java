package com.tang.demo.registry;

import java.net.InetSocketAddress;

public interface Registry {

    final String ROOT = "/ZK_Services";

    boolean register(InetSocketAddress addr, String serviceName);

}
