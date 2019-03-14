package netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端的处理
 *
 * @author Tang
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 向服务器发送指令
     *
     * @param ctx 通道上下文
     * @throws Exception 抛出异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);

        //发生粘包的问题
//		for (int i = 0; i<10; i++){
//			byte[] req = "QUERY TIME ORDER".getBytes();
//			ByteBuf firstMessage = Unpooled.buffer(req.length);
//			firstMessage.writeBytes(req);
//			ctx.writeAndFlush(firstMessage);
//		}
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered......" +  ctx.channel());
    }

    /**
     * 接收服务器的响应
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        //buf.readableBytes():获取缓冲区中可读的字节数；
        //根据可读字节数创建数组
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is : " + body);
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        ctx.close();
    }

}
