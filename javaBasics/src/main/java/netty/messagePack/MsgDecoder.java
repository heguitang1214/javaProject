//package netty.messagePack;
//
//import java.util.List;
//
//import org.msgpack.MessagePack;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageDecoder;
//
///**
// *
// * @author 小五老师-云析学院
// * @createTime 2019年3月5日 下午5:16:41
// * MessagePack 解码器
// */
//public class MsgDecoder extends MessageToMessageDecoder<ByteBuf> {
//
//	@Override
//	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//		//消息有多少可读
//		final int length = msg.readableBytes();
//		byte[] array = new byte[length];
//		//将ByteBuf中数据读入array字节数组
//		msg.getBytes(msg.readerIndex(), array, 0, length);
//		//从ByteBuf中读出的数据，通过MessagePack.read(byte[])进行解码
//		out.add(new MessagePack().read(array, UserVO.class));
//	}
//
//}
