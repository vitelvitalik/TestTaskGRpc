package org.example;

import com.test.task.TestTaskServiceGrpc;
import com.test.task.TestTaskServiceOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * Описание
 *
 * @author v.kiselev
 */
@Slf4j
public class TestTaskServiceImpl extends TestTaskServiceGrpc.TestTaskServiceImplBase {
    @Override
    public void getValues(TestTaskServiceOuterClass.ServerRequest request,
                          StreamObserver<TestTaskServiceOuterClass.ServerResponse> responseObserver) {

        System.out.println(request);

        int firstValue = request.getFirstValue();
        int lastValue = request.getLastValue();

        for (int i = firstValue; i < lastValue; i++) {

            firstValue = firstValue + 1;

            TestTaskServiceOuterClass.ServerResponse response = TestTaskServiceOuterClass
                    .ServerResponse.newBuilder()
                    .setCurrentValue(firstValue)
                    .build();

            responseObserver.onNext(response);
            log.info("new value:" + firstValue);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }

        }

        responseObserver.onCompleted();
    }
}
