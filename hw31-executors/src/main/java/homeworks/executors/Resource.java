package homeworks.executors;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

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

    public synchronized boolean isStopped() {
        return stopped;
    }

    public void announceCounter() {
        System.out.println(Thread.currentThread().getName() + " : " + counter);
    }

    public void increment() {
        counter.incrementAndGet();
    }

    public void decrement() {
        counter.decrementAndGet();
    }

    public void add(int delta) {
        counter.addAndGet(delta);
    }

}
