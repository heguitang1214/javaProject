package iomodel.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午3:17:06
 *
 */
public class TimeServer {
	public static void main(String[] args) {
		int port=8080; //服务端默认端口
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("The time server is start in port:"+port);
			Socket socket = null;
			while(true){
				socket = server.accept(); //等待客户端连接
				new Thread(new TimeServerHandler(socket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server!=null){
				System.out.println("The time server is close.");
				try {
					server.close();
					server = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
