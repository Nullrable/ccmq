package io.cc.mq.client;

import io.cc.mq.model.CCMessage;

/**
 * @author nhsoft.lsd
 */
public class CCProducer {

    private final CCBroker broker;

    public CCProducer(CCBroker broker) {
        this.broker = broker;
    }

    public void send(final String topic, final CCMessage<?> message) {
        broker.send(topic, message);
    }
}
