package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Start server
 */
@Slf4j
public class AppServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8585)
                .addService(new TestTaskServiceImpl())
                .build();

        server.start();

        log.info("Server started");

        server.awaitTermination();
    }
}
