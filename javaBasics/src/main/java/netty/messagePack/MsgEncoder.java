//package netty.messagePack;
//
//import org.msgpack.MessagePack;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//
///**
// *
// * @author 小五老师-云析学院
// * @createTime 2019年3月5日 下午5:12:33
// * MessagePack 编码器
// */
//public class MsgEncoder extends MessageToByteEncoder<UserVO> {
//
//	@Override
//	protected void encode(ChannelHandlerContext ctx, UserVO msg, ByteBuf out) throws Exception {
//		out.writeBytes(new MessagePack().write(msg));
//	}
//
//}
