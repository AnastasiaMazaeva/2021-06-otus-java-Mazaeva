package homeworks.executors;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class Resource {

    @Setter
    private boolean stopped = false;
    @Getter
    @Setter
    private int sharedBy = 0;
    @Setter
    @Getter
    private int priority;
    private final AtomicInteger counter = new AtomicInteger(1);

    public Resource(int priority) {
        this.priority = priority;
    }

    public int getCounter() {
        return counter.get();
    }

    public boolean isStopped() {
        return stopped;
    }

    public void add(int delta) {
        counter.addAndGet(delta);
    }

}
