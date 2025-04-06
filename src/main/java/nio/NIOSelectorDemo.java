package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/25 10:33
 */
public class NIOSelectorDemo {


    public static void main(String[] args) throws IOException {
        // 创建NIO channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9001));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // 多路复用器
        // 打开Selector 处理 channel, 即创建epoll
        Selector selector = Selector.open();

        // 状态事件
        // OP_ACCEPT 连接接受事件
        // OP_CONNECT 连接状态事件
        // OP_WRITE 写入数据的事件
        // OP_READ 读取数据的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server started success!!!");

        while (true) {

            /*
             * EPollArrayWrap
             * 调用native epoll_create 方法， linux 内核epoll_create 来创建epoll(struct 类似 object)实例
             *
             */
            // 阻塞 等待 需要处理的时间发生
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // OP_ACCEPT 连接获取和连接注册 事件
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    // 这里只注册了读事件， 如果需要给客户端写数据 则添加注册 写事件
                    channel.register(selector, SelectionKey.OP_READ);
                    System.out.println("Client connecting success!!!");
                    // OP_READ 读取数据时间
                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buff = ByteBuffer.allocate(6);
                    var len = socketChannel.read(buff);
                    if (len > 0) {
                        System.out.printf("Received:%s\n", new String(buff.array()));
                    } else if (-1 == len) {
                        socketChannel.close();
                        System.out.println("Client Disconnected");
                    }
                    // OP_WRITE 写入数据事件
                } else if (key.isWritable()) {

                } else if (!key.isConnectable()) {

                }
                // 从时间集合中 删除 本次处理的key, 防止下次selector重复处理
                iterator.remove();
            }

        }
    }
}
