package homeworks.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorsHome {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        service.schedule(new CustomThread("T1"), 0, TimeUnit.SECONDS);
        service.schedule(new CustomThread("T2"), 1, TimeUnit.SECONDS);
        service.shutdown();
    }
}
