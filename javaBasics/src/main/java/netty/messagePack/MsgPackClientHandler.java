//package ai.yunxi.messagePack;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//
///**
// *
// * @author Five
// * @createTime 2017年11月13日 下午9:16:37
// *
// */
//public class MsgPackClientHandler extends ChannelInboundHandlerAdapter {
//
//	@Override
//	//向服务器发送指令
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			UserVO user = new UserVO();
//			user.setId("1");
//			user.setName("Five");
//			ctx.writeAndFlush(user);
//		}
//	}
//
//	@Override
//	//接收服务器的响应
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("客户端接收到msgpack消息："+msg);
//		UserVO buf = (UserVO) msg;
//		System.out.println(buf);
//	}
//
//	@Override
//	//异常处理
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		//释放资源
//		ctx.close();
//	}
//
//}
