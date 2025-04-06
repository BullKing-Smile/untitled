package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/25 13:54
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("Channel Registered");

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("Channel Unregistered");
    }

    /**
     * 客户端连接完成就会自动触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("Client connection success!!!!");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("Channel Inactive");
    }

    /**
     * 当Channel中有来自客户端发送的数据 时回调， 对于netty来说就是 read
     *
     * @param ctx
     * @param msg 收到 客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        try {
            if (msg instanceof ByteBuf buf) {
                var len = buf.readableBytes();
                var b = new byte[len];
                if (buf.isReadable() && buf.refCnt() > 0) {

                    buf.readBytes(b, 0, len);
                    String data = new String(b);
                    System.out.println("Received data:" + data);

                    buf.release();

                    // response
                    ByteBuf bf = Unpooled.copiedBuffer("thanks client " + (Calendar.getInstance().getTimeInMillis()), CharsetUtil.UTF_8);
//                    ctx.writeAndFlush(bf);
                    ctx.channel().writeAndFlush(bf);
                }

            } /*else if (msg instanceof String msgString) {
                System.out.printf("Received data: %s\n", msgString);
                ByteBuf buf = Unpooled.copiedBuffer("hello world!!!", CharsetUtil.UTF_8);
                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
                HttpHeaders header = response.headers();
                header.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                header.set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
                ctx.writeAndFlush(response)
                        .addListener(ChannelFutureListener.CLOSE);
            }*/
        } catch (Exception e) {
          e.printStackTrace();
//          ctx.close();
        } finally {

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.println("Read complete");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("Channel Inactive" + evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        System.out.println("Channel Writability Changed");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("Exception Caught：");
        cause.printStackTrace();
        ctx.close();//发生异常时 关闭channel
    }
}
