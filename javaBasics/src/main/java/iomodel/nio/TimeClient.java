package iomodel.nio;


/**
 * BIO:客户端
 *
 * @author Tang
 */
public class TimeClient {

    public static void main(String[] args) {
        //服务端默认端口
        int port = 8080;
        new Thread(new TimeClientHandler("127.0.0.1", port), "NIO-TimeServerClient-001").start();
    }
}
