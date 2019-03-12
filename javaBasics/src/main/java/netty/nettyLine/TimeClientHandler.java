package ai.yunxi.nettyLine;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午9:16:37
 *
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	private byte[] req;

	@Override
	//向服务器发送指令
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message=null;
		//模拟一百次请求，发送重复内容
		for (int i = 0; i < 200; i++) {
			req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();
			message=Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}

	}

	@Override
	//接收服务器的响应
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		ByteBuf buf = (ByteBuf) msg;
//		//buf.readableBytes():获取缓冲区中可读的字节数；
//		//根据可读字节数创建数组
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "UTF-8");
		String body = (String) msg;
		System.out.println("Now is : "+body+". the counter is : "+ ++counter);
	}

	@Override
	//异常处理
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//释放资源
		ctx.close();
	}

}
