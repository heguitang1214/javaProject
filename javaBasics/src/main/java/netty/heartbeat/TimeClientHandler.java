package netty.heartbeat;

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

	@Override
	//向服务器发送指令
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuf firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
		ctx.writeAndFlush(firstMessage);


		Thread thread = new Thread(new HeartBeatThread(ctx));
		thread.start();
	}

	private class HeartBeatThread implements Runnable{
		ChannelHandlerContext ctx;

		/**
		 * @param ctx
		 */
		public HeartBeatThread(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
		}

		public void run() {
			while(true){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				byte[] hb = "hb".getBytes();
				ByteBuf hbMessage = Unpooled.buffer(hb.length);
				hbMessage.writeBytes(hb);
				ctx.writeAndFlush(hbMessage);
			}
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("---------连接断开！--------");
		Thread.sleep(10000);
		new TimeClient().connect(8080, "localhost");
	}

	@Override
	//接收服务器的响应
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		//buf.readableBytes():获取缓冲区中可读的字节数；
		//根据可读字节数创建数组
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is : "+body);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		System.out.println("IdleStateHandler事件触发");
	}

}
