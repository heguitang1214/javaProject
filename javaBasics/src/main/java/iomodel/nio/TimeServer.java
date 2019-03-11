package iomodel.nio;

/**
 * BIO:服务端
 *
 * @author Tang
 */
public class TimeServer {

    public static void main(String[] args) {
        //服务端默认端口
        int port = 8081;
        TimeServerHandler timeServer = new TimeServerHandler(port);
        new Thread(timeServer, "NIO-TimeServerHandler-001").start();
    }
}
