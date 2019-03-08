package iomodel.nio;
/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午5:21:33
 *
 */
public class TimeServerClient {

	public static void main(String[] args) {
		int port=8080; //服务端默认端口
		new Thread(new TimeClientHandler("127.0.0.1", port), "NIO-TimeServerClient-001").start();
	}
}
