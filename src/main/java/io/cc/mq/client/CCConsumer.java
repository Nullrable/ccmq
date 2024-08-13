package io.cc.mq.client;

import io.cc.mq.model.CCMessage;
import lombok.Getter;

/**
 * @author nhsoft.lsd
 */
public class CCConsumer {

    private String cid;

    @Getter
    private Listener listener;

    public CCConsumer(final String cid) {
        this.cid = cid;
    }

    public void sub(String topic) {
        MQClient.sub(topic, cid);
    }

    public void unsub(String topic) {
        MQClient.unsub(topic, cid);
    }

    public CCMessage recv(String topic) {
        return MQClient.recv(topic, cid);
    }

    public CCMessage recv(String topic, int index) {
        return MQClient.recv(topic, cid, index);
    }


    public int ack(String topic, CCMessage message) {
        return MQClient.ack(topic, cid, message);
    }

    public void addListener(String topic, Listener listener) {
        this.listener = listener;
        ConsumerScheduler.addConsumer(topic, this);
    }
}
