package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/25 13:43
 */
public class NettyDemo {
    public static void main(String[] args) throws InterruptedException {
        // 定义两个线程组(线程池)
        // bossGroup 主要是处理与客户端的连接， 再将建立好的连接 交给 workGroup线程组
        // OP_ACCEPT 事件
//        EventLoopGroup bossGroup = new EpollEventLoopGroup(1); // 接受连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        // workerGroup 主要是处理线程事件
        // OP_READ/WRITE 事件
//        EventLoopGroup workerGroup = new EpollEventLoopGroup(); // 处理读写操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();
         try {
            ServerBootstrap bootstrap = new ServerBootstrap();


            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 指定使用NIO传输/通讯
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 指定处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 对 workerGroup的SocketChannel设置事件处理器
                            socketChannel.pipeline()
//                                    .addLast(new StringEncoder())
//                                    .addLast(new StringDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });


            // 绑定端口 并启动服务器
             // bind()方法 的执行是异步的
             // sync() 方法会使bind()操作和后续代码的执行由异步变为同步
            var cf = bootstrap.bind(9002).sync();
            // 等待Channel 服务套接字关闭
             // closeFuture()的执行是异步的
             // sync() closeFuture()操作和后续代码的执行由异步变为同步
            cf.channel().closeFuture().sync();
        } finally {
             // 优雅的退出， 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
