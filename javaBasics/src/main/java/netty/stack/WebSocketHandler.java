package netty.stack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author 小五老师-云析学院
 * @createTime 2019年3月10日 下午5:11:39
 */
public class WebSocketHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TextWebSocketFrame message = (TextWebSocketFrame) msg;
        System.out.println("WebSocket 请求，  msg:" + message.text());

        TextWebSocketFrame fram = new TextWebSocketFrame("是谁？");
        ctx.writeAndFlush(fram);
    }
}
