package io.cc.mq.server;

import io.cc.mq.model.CCMessage;
import io.cc.mq.model.Subscription;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author nhsoft.lsd
 */
public class BrokerManager {

    private static final BrokerKeeper brokerKeeper = new BrokerKeeper();
    private static final Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();//key: topic

    protected void sub(final Subscription subscription) {
        subscriptions.putIfAbsent(subscription.getKey(), subscription);
    }

    protected void unsub(final Subscription subscription) {
        subscriptions.remove(subscription.getKey());
    }

    protected void send(final String topic, final CCMessage message) {
        brokerKeeper.send(topic, message);
    }

    protected CCMessage recv(final String consumerId, final String topic) {
        Subscription subscription = subscriptions.get(Subscription.getKey(topic, consumerId));
        if (subscription == null) {
            throw new RuntimeException("topic [" + topic + "] consumerId [" + consumerId + "] subscription not found");
        }
        int offset = subscription.getOffset();
        int nextOffset = offset + 1;
        CCMessage message = brokerKeeper.recv(topic, nextOffset);
        message.getHeaders().put("x-offset", nextOffset + "");
        return message;
    }

    protected CCMessage recv(final String consumerId, final String topic, int index) {
        Subscription subscription = subscriptions.get(Subscription.getKey(topic, consumerId));
        if (subscription == null) {
            throw new RuntimeException("topic [" + topic + "] consumerId [" + consumerId + "] subscription not found");
        }
        return brokerKeeper.recv(topic, index);
    }

    protected int ack(final String consumerId, final String topic, final int offset) {
        Subscription subscription = subscriptions.get(Subscription.getKey(topic, consumerId));
        if (subscription == null) {
            throw new RuntimeException("topic [" + topic + "] consumerId [" + consumerId + "] subscription not found");
        }
        if (offset > subscription.getOffset() && offset <= brokerKeeper.getQueueIndex(topic)) {
            subscription.setOffset(offset);
            return offset;
        } else {
            return -1;
        }

    }
}
