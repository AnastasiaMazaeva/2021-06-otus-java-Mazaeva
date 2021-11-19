package homeworks.executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Executors {

    private static final int sharingThreads = 2;
    private static final int maxPriority = Thread.NORM_PRIORITY;
    private static final Resource resource = new Resource(maxPriority); // static!

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < sharingThreads; ++i) {
            Subscriber subscriber = new Subscriber(resource, sharingThreads);
            subscriber.setPriority(maxPriority - i);
            subscriber.start();
        }

        for (int i = 0; i < 9; ++i) {
            syncCall(1);
        }

        for (int i = 10; i > 1; --i) {
            syncCall(-1);
        }
        resource.setStopped(true);
    }

    public static void syncCall(int delta) throws InterruptedException {
        synchronized (resource) {
            while (resource.getSharedBy() < sharingThreads) {
                resource.wait();
            }
            resource.add(delta);
            resource.setSharedBy(0);
            resource.setPriority(maxPriority);
            resource.notifyAll();
        }
    }

}
