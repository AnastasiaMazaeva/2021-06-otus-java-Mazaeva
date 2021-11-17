package homeworks.executors;

public class Executors {

    private static final Resource resource = new Resource(); // static!
    public static void main(String[] args) throws InterruptedException {
        int sharingThreads = 2;

        for (int i = 0; i < sharingThreads; ++i) {
            Subscriber subscriber = new Subscriber(resource, sharingThreads);
            subscriber.setPriority(10 - i);
            subscriber.start();
        }

        for (int i = 0; i < 9; ++i) {
            synchronized (resource) {
                while (resource.getSharedBy() < sharingThreads) {
                    resource.wait();
                }
                resource.increment();
                resource.setSharedBy(0);
                resource.notifyAll();
            }
        }

        for (int i = 10; i > 1; --i) {
            synchronized (resource) {
                while (resource.getSharedBy() < sharingThreads) {
                    resource.wait();
                }
                resource.decrement();
                resource.setSharedBy(0);
                resource.notifyAll();
            }
        }
        resource.setStopped(true);
    }

}
