package io.cc.mq.client;

import io.cc.mq.model.CCMessage;

/**
 * @author nhsoft.lsd
 */
public interface Listener {

    void onMessage(String topic, CCMessage message);
}
