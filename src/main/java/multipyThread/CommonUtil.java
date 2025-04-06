package multipyThread;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @auth Felix
 * @since 2025/3/1 23:56
 */
public class CommonUtil {

    public static String readFile(String path) {
        try {
           return Files.readString(Paths.get(path), CharsetUtil.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void sleepMillis(long millis) {
        try {
            TimeUnit.MINUTES.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printMessage(String message) {
        String str = new StringJoiner("|")
                .add("\n"+System.currentTimeMillis())
                .add("Thread ID="+Thread.currentThread().getId())
                .add("Thread Name="+Thread.currentThread().getName())
                .add(message)
                .toString();
        System.out.println(str);
    }
}
