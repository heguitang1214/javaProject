package iomodel.nio;
/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午4:54:54
 *
 */
public class TimeServer {

	public static void main(String[] args) {
		int port=8081; //服务端默认端口
		TimeServerHandler timeServer=new TimeServerHandler(port);
		new Thread(timeServer, "NIO-TimeServerHandler-001").start();
	}
}
