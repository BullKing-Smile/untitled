package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/25 2:45
 */
public class NIODemo {
    public static void main(String[] args) throws IOException {

        ArrayList<SocketChannel> channels = new ArrayList<>();

        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.socket().bind(new InetSocketAddress(9991));
        // !!!Attention!!! 配置为非阻塞模式
        socketChannel.configureBlocking(false);
        while (true) {
            // NIO的非阻塞 由操作系统内部实现，底层调用linux内核的accept函数
            // 接收客户端连接
            SocketChannel channel = socketChannel.accept();
            // 不为空则表示  连接成功
            if (null != channel) {
                System.out.println("Connecting Success");
                // 将客户端连接 设置为 非阻塞
                channel.configureBlocking(false);
                channels.add(channel);

            }

            Iterator<SocketChannel> iterator = channels.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();

                ByteBuffer buff = ByteBuffer.allocate(6);
                var len = sc.read(buff);
                if (len > 0) {
                    System.out.printf("Received:%s\n", new String(buff.array()));
                } else if (-1 == len){
                    iterator.remove();
                    System.out.println("Disconnected");
                }
            }

        }
    }
}
