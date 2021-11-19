package homeworks.executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Subscriber extends Thread {

    private final Resource resource;
    private final int resourceSharedBy;
    private int currentValue = 0;

    public Subscriber(Resource resource, int resourceSharedBy) {
        this.resource = resource;
        this.resourceSharedBy = resourceSharedBy;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (resource) {
                try {
                    while (currentValue == resource.getCounter()
                            || resourceSharedBy == resource.getSharedBy()
                            || resource.getPriority() > this.getPriority()) {
                        resource.wait();
                    }
                    resource.announceCounter();
                    currentValue = resource.getCounter();
                    resource.setSharedBy(resource.getSharedBy() + 1);
                    resource.setPriority(resource.getPriority() - 1);
                    if (resource.isStopped()) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    resource.notifyAll();
                }
            }
        }
    }
}
