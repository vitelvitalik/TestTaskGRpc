package org.example.threads;

import com.test.task.TestTaskServiceOuterClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.List;

/**
 * Описание
 *
 * @author v.kiselev
 */
@Slf4j
public class ResponseThread extends Thread {

    private final Iterator<TestTaskServiceOuterClass.ServerResponse> response;
    private final List<Integer> valueList;

    public ResponseThread(Iterator<TestTaskServiceOuterClass.ServerResponse> response,
                          List<Integer> valueList) {
        this.response = response;
        this.valueList = valueList;
    }

    @Override
    public void run() {
        while (response.hasNext()) {
            valueList.add(response.next().getCurrentValue());
            log.info(String.format("new value: [%s]", valueList.get(valueList.size() - 1)));
        }
        log.info("request completed");
        this.interrupt();
    }
}
