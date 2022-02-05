import io.grpc.stub.StreamObserver;

public class SequenceServiceImpl extends SequenceServiceGrpc.SequenceServiceImplBase {

    @Override
    public void askForSequence(Sequence.SequenceRequest request, StreamObserver<Sequence.SequenceResponse> responseObserver) {
        for (int i = request.getFirstValue(); i < request.getLastValue(); i++) {
            responseObserver.onNext(Sequence.SequenceResponse.newBuilder().setCurrentValue(i).build());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}
