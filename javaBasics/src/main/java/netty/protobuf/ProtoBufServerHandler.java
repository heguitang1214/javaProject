//package netty.protobuf;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//
///**
// *
// * @author Five
// * @createTime 2017年11月13日 下午8:57:51
// * @readme 用于对网络时间进行读写操作，通常我们只需要关注channelRead和exceptionCaught方法。
// *
// */
//public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {
//
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("服务端接收到msgpack消息："+msg);
//		UserProto.UserProtocol buf = (UserProto.UserProtocol) msg;
//		System.out.println(buf);
//
//		System.out.println("发送数据"+buf.getSerializedSize());
//		ctx.writeAndFlush(buf);
//	}
//}
