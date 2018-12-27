package com.zero.demo.registry;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServiceInvoke<T> {

	@SuppressWarnings("unchecked")
	public static <T> T remoteCall(final Class<?> serviceInterface) {
		String serverInfo = ServiceDiscovery.getServer(serviceInterface.getName()).get();
		String host = serverInfo.substring(0, serverInfo.indexOf(":"));
		String port = serverInfo.substring(serverInfo.indexOf(":") + 1, serverInfo.length());
		InetSocketAddress addr = new InetSocketAddress(host, Integer.valueOf(port));

		return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(),
				new Class<?>[] { serviceInterface }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						Socket socket = null;
						// 接受服务器返回的数据
						ObjectInputStream in = null;
						// 往服务器写数据（调用数据）
						ObjectOutputStream out = null;
						try {
							// 建立socket连接
							socket = new Socket();
							socket.connect(addr);

							// 开始写数据
							out = new ObjectOutputStream(socket.getOutputStream());
							out.writeUTF(serviceInterface.getName());
							out.writeUTF(method.getName());
							out.writeObject(method.getParameterTypes());
							out.writeObject(args);

							// 获取服务器返回的数据
							in = new ObjectInputStream(socket.getInputStream());
							return in.readObject();

						} finally {
							if (socket != null)
								socket.close();
						}
					}
				});
	}
}
