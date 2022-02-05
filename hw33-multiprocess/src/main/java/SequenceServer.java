import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SequenceServer {
    private static final int PORT = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        SequenceServiceImpl sequenceService = new SequenceServiceImpl();
        Server server = ServerBuilder.forPort(PORT).addService(sequenceService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}