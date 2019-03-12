package netty.nettyDelimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 *
 * @author Five
 * @createTime 2017年11月13日 下午9:12:29
 *
 */
public class TimeClient {
	public static void main(String[] args) throws Exception {
		int port=8080; //服务端默认端口
		new TimeClient().connect(port, "127.0.0.1");
	}
	public void connect(int port, String host) throws Exception{
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bs = new Bootstrap();
			bs.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						//创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
						protected void initChannel(SocketChannel arg0) throws Exception {
							//处理粘包/拆包问题
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							arg0.pipeline().addLast(new StringDecoder());

							arg0.pipeline().addLast(new TimeClientHandler());
						}
					});
			//发起异步连接操作
			ChannelFuture cf = bs.connect(host, port).sync();
			//等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
}
