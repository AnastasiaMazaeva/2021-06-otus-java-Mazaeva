package homeworks.executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Executors {

    private int counter = 1;
    private String lastThreadName = "second";
    private static final String lastThreadReading = "second";

    private synchronized void action(String currentThreadName) {
        try {
            for (int i = 1; i < 10; i++) {
                while (lastThreadName.equals(currentThreadName)) {
                    this.wait();
                }
                Thread.sleep(1_000);
                log.info("{} : {}", Thread.currentThread().getName(), counter);
                if (currentThreadName.equals(lastThreadReading)) {
                    counter++;
                }
                lastThreadName = currentThreadName;
                this.notifyAll();
            }

            for (int i = 10; i > 0; i--) {
                while (lastThreadName.equals(currentThreadName)) {
                    this.wait();
                }
                Thread.sleep(1_000);
                log.info("{} : {}", Thread.currentThread().getName(), counter);
                if (currentThreadName.equals(lastThreadReading)) {
                    counter--;
                }
                lastThreadName = currentThreadName;
                this.notifyAll();
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {

        Executors executors = new Executors();
        new Thread(() -> executors.action("first")).start();
        new Thread(() -> executors.action("second")).start();
    }


}
