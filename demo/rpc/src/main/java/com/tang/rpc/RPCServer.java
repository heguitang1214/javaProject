package com.tang.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RPC服务端
 */
public class RPCServer implements Registry {
    //启动服务
    public static void main(String[] args) {
        RPCServer server = new RPCServer(8080);
        server.register(DemoService.class, DemoServiceImpl.class);
        server.start();
    }

    // 服务器端口
    private int port;

    // 注册中心（会所的花名册）
    private Map<String, Class> serviceCenter = new HashMap<>();

    // 建立一个固定大小的连接池：线程核心数=cpu数
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    // 简单注册中心
    public void register(Class serviceInterface, Class clazz) {
        serviceCenter.put(serviceInterface.getName(), clazz);
    }

    //构造函数
    private RPCServer(int port) {
        this.port = port;
    }

    //服务器服务的过程
    private void start() {
        //BIO socket编程
        ServerSocket serverSocket;
        try {
            // 启动服务器的过程
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(port));
            System.out.println("启动RPCServer服务器......");
            // 不断接受服务器请求
            for (; ; ) {
                // 接受客户端的请求(把请求封装成Runnable)，交给线程池去处理
                executorService.execute(new ServiceTask(serverSocket.accept()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 处理客户端请求类
    private class ServiceTask implements Runnable {
        Socket socket;

        ServiceTask(Socket accept) {
            this.socket = accept;
        }

        @Override
        // 处理客户端请求的过程（按照客户端发送数据的顺序）
        public void run() {
            // 读取客户端的信息
            ObjectInputStream in;
            // 往客户端写入的信息
            ObjectOutputStream out;

            try {
                // 读取客户端请求
                in = new ObjectInputStream(socket.getInputStream());
                // 获取服务名（小妹名字）
                String serviceName = in.readUTF();
                // 获取函数名字（小妹提供什么服务）
                String methodName = in.readUTF();
                // 获取参数类型
                Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
                // 获取参数
                Object[] args = (Object[]) in.readObject();

                // 从注册中心获取服务（从花名册中挑选小妹）
                Class serviceClass = serviceCenter.get(serviceName);
                if (serviceClass == null) {
                    throw new ClassNotFoundException(serviceName + " not found");
                }

                // 获取方法
                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), args);
                // 把结果返回给客户端
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
