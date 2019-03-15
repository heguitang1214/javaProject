package netty.stack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * HTTP协议栈开发
 * @author Tang
 */
public class HttpTimeServer {

    public static void main(String[] args) throws Exception {
        int port = 8080; //服务端默认端口
        new HttpTimeServer().bind(port);
    }

    public void bind(int port) throws Exception {
        //1用于服务端接受客户端的连接
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //2用于进行SocketChannel的网络读写
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            //Netty用于启动NIO服务器的辅助启动类
            ServerBootstrap sb = new ServerBootstrap();
            //将两个NIO线程组传入辅助启动类中
            sb.group(parentGroup, childGroup)
                    //设置创建的Channel为NioServerSocketChannel类型
                    .channel(NioServerSocketChannel.class)
                    //配置NioServerSocketChannel的TCP参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //设置绑定IO事件的处理类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
                        @Override
                        protected void initChannel(SocketChannel arg0) throws Exception {
                            ChannelPipeline pipeline = arg0.pipeline();
                            //支持Http协议
                            //Http请求处理的编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //用于将HTTP请求进行封装为FullHttpRequest对象
                            pipeline.addLast(new HttpObjectAggregator(1024 * 64));
                            //处理文件流
                            pipeline.addLast(new ChunkedWriteHandler());
                            //Http请求的具体处理对象
                            pipeline.addLast(new HttpHandler());
                        }
                    });
            //绑定端口，同步等待成功（sync()：同步阻塞方法，等待bind操作完成才继续）
            //ChannelFuture主要用于异步操作的通知回调
            ChannelFuture cf = sb.bind(port).sync();
            System.out.println("服务端启动在8080端口。");
            //等待服务端监听端口关闭
            cf.channel().closeFuture().sync();
        } finally {
            //优雅退出，释放线程池资源
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}

