package homeworks.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsHome {
    private static final Logger log = LoggerFactory.getLogger(ExecutorsHome.class);
    private static final ExecutorService service = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i <= 10; ++i) {
            executeTasks(i);
        }

        for (int i = 9; i >= 1; --i) {
            executeTasks(i);
        }
        service.shutdown();
    }

    private static void executeTasks(int counter) throws InterruptedException, ExecutionException {
        Future<Integer> futureFirst = service.submit(() -> task(counter));
        Future<Integer> futureSecond = service.submit(() -> task(counter));
        log.info("T1 : " + futureFirst.get());
        log.info("T2 : " + futureSecond.get());
    }

    private static int task(int id) {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return id;
    }
}
