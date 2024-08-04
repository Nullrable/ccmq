package io.cc.mq.client;

import io.cc.mq.model.CCMessage;

/**
 * @author nhsoft.lsd
 */
public class CCProducer {

    private final MQClient client;

    public CCProducer(MQClient broker) {
        this.client = broker;
    }

    public void send(final String topic, final CCMessage<?> message) {
        client.send(topic, message);
    }
}
