//package netty.protobuf;
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
//public class ProtoBufClientHandler extends ChannelInboundHandlerAdapter {
//
//	@Override
//	//向服务器发送指令
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		UserProto.UserProtocol user = UserProto.UserProtocol.newBuilder()
//				.setId("1")
//				.setName("Five")
//				.build();
//		ctx.writeAndFlush(user);
//	}
//
//	@Override
//	//接收服务器的响应
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("客户端接收到msgpack消息："+msg);
//		UserProto.UserProtocol buf = (UserProto.UserProtocol) msg;
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
