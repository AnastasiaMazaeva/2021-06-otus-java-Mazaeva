package homeworks.executors;

public class CustomThread extends Thread {

    private final String name;

    public CustomThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; ++i) {
                System.out.println(this.name + " : " + i + " ");
                Thread.sleep(1_000);
            }
            for (int i = 9; i >= 1; --i) {
                System.out.println(this.name + " : " + i + " ");
                Thread.sleep(1_000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
