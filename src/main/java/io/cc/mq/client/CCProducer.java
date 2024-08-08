package io.cc.mq.client;

import io.cc.mq.model.CCMessage;

/**
 * @author nhsoft.lsd
 */
public class CCProducer {

    public void send(final String topic, final CCMessage message) {
        MQClient.send(topic, message);
    }
}
