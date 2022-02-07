import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class SequenceClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5051;
    private static final Object monitor = new Object();
    private static int serverValue;

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("Client is starting...");
        SequenceServiceGrpc.SequenceServiceStub stub = SequenceServiceGrpc.newStub(channel);
        stub.askForSequence(Sequence.SequenceRequest.newBuilder().setFirstValue(1).setLastValue(30).build(), new StreamObserver<>() {
            @Override
            public void onNext(Sequence.SequenceResponse value) {
                System.out.println("Server sent value : " + value.getCurrentValue());
                setValue(value.getCurrentValue());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nAll done!");
                latch.countDown();
            }
        });

        int currentValue = 0;
        for (int i = 0; i < 50 ; ++i) {
            System.out.println("Current value : " + currentValue);
            Thread.sleep(1000);
            currentValue = getCurrentValue(currentValue);
        }
        System.out.println("Client done counting");

        latch.await();

        channel.shutdown();
    }

    private static int getCurrentValue(int currentValue) {
        synchronized (monitor) {
            int result = currentValue + serverValue + 1;
            serverValue = 0;
            return result;
        }
    }

    private static void setValue(int value) {
        synchronized (monitor) {
            serverValue = value;
        }
    }
}