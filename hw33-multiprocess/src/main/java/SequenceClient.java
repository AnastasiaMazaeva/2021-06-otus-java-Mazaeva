import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 50051;

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger serverValue = new AtomicInteger(0);
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
                serverValue.set(value.getCurrentValue());
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
            currentValue = currentValue + serverValue.getAndSet(0) + 1;
        }
        System.out.println("Client done counting");

        latch.await();

        channel.shutdown();
    }
}