package netty.netty;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午8:57:51
 * @readme 用于对网络时间进行读写操作，通常我们只需要关注channelRead和exceptionCaught方法。
 *
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		//buf.readableBytes():获取缓冲区中可读的字节数；
		//根据可读字节数创建数组
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("The time server(Thread:"+Thread.currentThread()+") receive order : "+body);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";

		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		//将待发送的消息放到发送缓存数组中
		ctx.writeAndFlush(resp);
	}
}
