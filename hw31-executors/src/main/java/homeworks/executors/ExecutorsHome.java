package homeworks.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsHome {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        Runnable runnable = () -> {
            try {
                for (int i = 1; i <= 10; ++i) {
                    System.out.println(Thread.currentThread().getName() + " : " + i + " ");
                    Thread.sleep(1_000);
                }
                for (int i = 9; i >= 1; --i) {
                    System.out.println(Thread.currentThread().getName() + " : " + i + " ");
                    Thread.sleep(1_000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        service.schedule(runnable, 0, TimeUnit.SECONDS);
        service.schedule(runnable, 1, TimeUnit.SECONDS);
        service.shutdown();
    }
}
