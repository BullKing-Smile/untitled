package multipyThread;

import java.util.concurrent.TimeUnit;

/**
 * @auth Felix
 * @since 2025/3/2 19:17
 */
public class MyTask {
    private Integer index;
    public MyTask(Integer index){
        this.index = index;
    }
    public void doWrok() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
