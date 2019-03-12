package netty.nettyFixedLength;

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

	private int counter;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The time server(Thread:"+Thread.currentThread()+") receive order : "+body+". the counter is : "+ ++counter);
		String currentTime = body;

		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

		//将待发送的消息放到发送缓存数组中
		ctx.writeAndFlush(resp);
	}
}
