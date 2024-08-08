package io.cc.mq.server;

import io.cc.mq.model.CCMessage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 负责将消息持久化到磁盘。
 *
 * @author nhsoft.lsd
 */
@Data
@AllArgsConstructor
public class BrokerKeeper {

    private static final Map<String, MessageQueue> messageQueues = new ConcurrentHashMap<>();

    public void send(final String topic, final CCMessage message) {
        if (!messageQueues.containsKey(topic)) {
            messageQueues.put(topic, new MessageQueue());
        }
        messageQueues.get(topic).add(message);
    }

    public CCMessage recv(final String topic, final int offset) {
        if (!messageQueues.containsKey(topic)) {
            messageQueues.put(topic, new MessageQueue());
        }
        CCMessage message = messageQueues.get(topic).get(offset);
        if (message == null) {
            return null;
        }
        message.getHeaders().put("x-offset", offset + "");
        return message;
    }

    public int getQueueIndex(final String topic) {
        if (!messageQueues.containsKey(topic)) {
            messageQueues.put(topic, new MessageQueue());
        }
        return messageQueues.get(topic).getIndex().get();
    }
}
