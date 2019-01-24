package com.zero.demo.registry;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 服务提供者
public class ServiceProvider {

	private static HashMap<String, Class<?>> serviceCenter = new HashMap<>();

	// 定义个线程池 来处理客户端的请求
	private static ExecutorService executors = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private ServiceRegister serviceRegister;

	// 服务器端口
	private int port;

	// 服务器构造函数
	public ServiceProvider(int port) {
		this.port = port;

		try {
			this.serviceRegister = new ServiceRegister("192.168.56.101:2181", 3000, 3000);
		} catch (Exception e) {
			System.err.println("不能初始化ServiceRegister");
		}
	}

	// 往ZooKeeper（服务注册中心）中注册服务
	public boolean register(String host, int port, Class<?> serviceInterface, Class<?> clazz) {
		InetSocketAddress addr = new InetSocketAddress(host, port);
		serviceCenter.put(serviceInterface.getName(), clazz);
		return serviceRegister.register(addr, serviceInterface.getName());
	}

	// 服务器服务的过程
	public void start() {
		ServerSocket server = null;
		// BIO Socket编程
		try {
			server = new ServerSocket();
			server.bind(new InetSocketAddress(port));
			System.out.println("启动服务器.....");
			// 开始对外服务
			while (true) {
				// 处理客户端传递的数据
				// 把接受到的数据交给线程池去处理
				executors.execute(new ServiceTask(server.accept()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private class ServiceTask implements Runnable {
		Socket socket = null;

		public ServiceTask(Socket accept) {
			this.socket = accept;
		}

		@Override
		public void run() {
			// 读客户端数据流
			ObjectInputStream in = null;
			// 往客户端写数据
			ObjectOutputStream out = null;
			try {
				// 读取客户发送的数据
				in = new ObjectInputStream(socket.getInputStream());
				// 从客户端发送的数据中读取服务名字
				String serviceName = in.readUTF();
				// 从客户端发送的数据中读取方法名字
				String methodName = in.readUTF();
				// 从客户端发送的数据中读取参数类型
				Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
				// 从客户端发送的数据中读取参数
				Object[] args = (Object[]) in.readObject();

				Class<?> serviceClass = serviceCenter.get(serviceName);
				if (serviceClass == null)
					throw new ClassNotFoundException(serviceName + " not found");

				// 调用服务
				Method method = serviceClass.getMethod(methodName, parameterTypes);
				Object result = method.invoke(serviceClass.newInstance(), args);

				// 把结果写到客户端
				out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ServiceProvider sp = new ServiceProvider(8083);
		sp.register("192.168.2.135", 8083, HelloService.class, HelloServiceImpl.class);
		sp.start();
	}

}
