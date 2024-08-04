package io.cc.mq.client;

import io.cc.mq.model.CCMessage;
import lombok.AllArgsConstructor;

/**
 * @author nhsoft.lsd
 */
@AllArgsConstructor
public class CCConsumer {

    private String topic;
    private CCBroker ccBroker;
    private String consumerId;

    // 消费消息
    public CCMessage<?> poll() {
        return ccBroker.recv(topic, consumerId);
    }
}
