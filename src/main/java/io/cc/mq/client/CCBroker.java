package io.cc.mq.client;

import io.cc.mq.model.CCMessage;
import io.cc.mq.model.Subscription;
import lombok.NoArgsConstructor;

/**
 * @author nhsoft.lsd
 */
@NoArgsConstructor
public class CCBroker {

    public void sub(final Subscription subscription) {

    }

    public void unsub(final Subscription subscription) {
    }

    public void send(final String topic, final CCMessage<?> message) {
    }

    public CCMessage<?> recv(final String consumerId, final String topic) {
        return null;
    }

    public CCMessage<?> recv(final String consumerId, final String topic, int index) {
        return null;
    }

    public int ack(final String consumerId, final String topic, final int offset) {
        return 0;
    }
}
