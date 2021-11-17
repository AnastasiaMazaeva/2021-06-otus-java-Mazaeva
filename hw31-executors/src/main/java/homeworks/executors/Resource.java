package homeworks.executors;

import java.util.concurrent.atomic.AtomicInteger;

public class Resource {

    private boolean stopped = false;
    private final AtomicInteger i = new AtomicInteger(1);

    private int sharedBy = 0;

    public int getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(int sharedBy) {
        this.sharedBy = sharedBy;
    }

    public void increment() {
        i.incrementAndGet();
    }

    public void decrement() {
        i.decrementAndGet();
    }

    public int getCounter() {
        return i.get();
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void announceCounter() {
        System.out.println(Thread.currentThread().getName() + " : " + i);
    }

}
