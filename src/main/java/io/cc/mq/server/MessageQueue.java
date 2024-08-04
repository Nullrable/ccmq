package io.cc.mq.server;

import io.cc.mq.model.CCMessage;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;

/**
 * @author nhsoft.lsd
 */
public class MessageQueue {

    private CCMessage<?>[] messages = new CCMessage[1024];
    @Getter
    private AtomicInteger index = new AtomicInteger(0);

    public CCMessage<?> get(final int ind) {
        if (ind < 0 || ind > index.get()) {
            return null;
        }
        return messages[ind];
    }

    public void add(final CCMessage<?> message) {
        int ind = index.incrementAndGet();
        if (ind > 1024) {
            throw new RuntimeException("message queue range over, max queue size is 1024");
        }
        messages[ind] = message;
    }
}
