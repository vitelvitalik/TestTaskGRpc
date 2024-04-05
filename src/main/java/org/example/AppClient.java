package org.example;

import com.test.task.TestTaskServiceGrpc;
import com.test.task.TestTaskServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.threads.ResponseThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Описание
 *
 * @author v.kiselev
 */
@Slf4j
public class AppClient {

    public static void main(String[] args) {

        int currentValue = 0;

        List<Integer> valueList = new ArrayList<>();

        log.info("numbers Client is starting...");

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:8585")
                .usePlaintext()
                .build();

        TestTaskServiceGrpc.TestTaskServiceBlockingStub stub = TestTaskServiceGrpc
                .newBlockingStub(channel);

        TestTaskServiceOuterClass.ServerRequest request = TestTaskServiceOuterClass
                .ServerRequest.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();


        Iterator<TestTaskServiceOuterClass.ServerResponse> response = stub.getValues(request);

        ResponseThread responseThread = new ResponseThread(response, valueList);
        responseThread.start();

        int v = 0;
        for (int i = 0; i < 50; i++) {
            int tmpSrv = valueList.isEmpty() ? 0 : valueList.get(valueList.size() - 1);

            currentValue = currentValue + (v == tmpSrv ? 0 : tmpSrv) + 1;
            log.info(String.format("currentValue: [%s]", currentValue));

            v = tmpSrv;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }

        }

        while (responseThread.isInterrupted()) {
            channel.shutdownNow();
        }
    }

}
