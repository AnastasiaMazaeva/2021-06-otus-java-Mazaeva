package homeworks.executors;

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
        while (!resource.isStopped()) {
            synchronized (resource) {
                try {
                    while (currentValue == resource.getCounter()
                            || resourceSharedBy == resource.getSharedBy()) {
                        if (resource.isStopped()) {
                            break;
                        }
                        resource.wait();
                    }
                    resource.announceCounter();
                    currentValue = resource.getCounter();
                    resource.setSharedBy(resource.getSharedBy() + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    resource.notifyAll();
                }
            }
        }
    }
}
