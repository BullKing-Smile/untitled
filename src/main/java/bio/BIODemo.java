package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author shenfei.wang@g42.ai
 * @Description
 * @Date 2025/2/25 1:33
 */
public class BIODemo {

    /**
     * 1. 打开Telnet控制台
     * 2. type 'o localhost 9000' 链接
     * 3. input 'x' 进入发送模式
     * 4. input 'sen xxxxx' 需要发送的内容
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9000)){
            while (true) {
                System.out.println("Waiting connection ...");
                // open telnet terminal
                // 'set escape x' ---- set input mode
                // 'o localhost 9000' ---- establish connection
                // input 'x' ---- 进入输入模式
                // then input 'sen xxxxxx' ---- send messages
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                System.out.println("Connecting Success");
                System.out.println("Handling data ...");
                handle(socket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理读取 客户端发送来的数据
     * 可以放到子线程中处理。。。， 但是会遇到C10K,C10M的问题 OOM
     */
    private static void handle(Socket socket) throws IOException {
        byte[] buff = new byte[1024];
        System.out.println("Ready to read ...");
        while (true) {
            int read = socket.getInputStream().read(buff);
//            System.out.println("Read done");
            if (-1 != read) {
                String data = new String(buff, 0, read, StandardCharsets.UTF_8);
//                System.out.printf("Received data is %s\n", data);
                System.out.println(data);
                if ("end".equalsIgnoreCase(data)) {
                    break;
                }
            }
        }
        socket.close();
        System.out.println("End");
    }
}
