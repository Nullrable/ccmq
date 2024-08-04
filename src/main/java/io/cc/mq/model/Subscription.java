package io.cc.mq.model;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;

/**
 * @author nhsoft.lsd
 */
@Data
public class Subscription {

    private String topic;

    private String consumerId;

    private AtomicInteger offset = new AtomicInteger(-1);

    public Subscription(final String topic, final String consumerId) {
        this.topic = topic;
        this.consumerId = consumerId;
    }

    public void setOffset(int index) {
        offset.set(index);
    }

    public int nextOffset() {
        return offset.incrementAndGet();
    }

    public String getKey() {
        return getKey(topic, consumerId);
    }

    public static String getKey(final String topic, final String consumerId) {
        return topic + ":" + consumerId;
    }


}
