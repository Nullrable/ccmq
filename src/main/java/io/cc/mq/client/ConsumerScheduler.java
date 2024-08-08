package io.cc.mq.client;

import io.cc.mq.model.CCMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author nhsoft.lsd
 */
public class ConsumerScheduler {

    private static MultiValueMap<String, CCConsumer> consumers = new LinkedMultiValueMap<>();
    private static ScheduledExecutorService executor;

    static {
        init();
    }

    private static void init() {
        executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleWithFixedDelay(
                () -> {
                    consumers.forEach((topic, consumers)-> {
                        System.out.println("wwrwerwerewrewr");
                        consumers.forEach(consumer-> {
                            CCMessage message = consumer.recv(topic);
                            if (message == null) return;
                            consumer.getListener().onMessage(topic, message);
                        });

                    });
                },
                1, 1000, TimeUnit.MILLISECONDS);
    }

    public static void addConsumer(String topic, CCConsumer consumer) {
        consumers.add(topic, consumer);
    }
}
