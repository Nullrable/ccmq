package io.cc.mq.server;

import com.alibaba.fastjson.JSON;
import io.cc.mq.model.CCMessage;
import io.cc.mq.store.Store;
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

    private static final Map<String, Store> stores = new ConcurrentHashMap<>();

    public void send(final String topic, final CCMessage message) {
        if (!stores.containsKey(topic)) {
            stores.putIfAbsent(topic, new Store(topic));
        }
        stores.get(topic).add(JSON.toJSONString(message));
    }

    public CCMessage recv(final String topic, final int offset) {
        if (!stores.containsKey(topic)) {
            return null;
        }
        Store store = stores.get(topic);
        int nextOffset = store.next_pos(offset);
        CCMessage message = JSON.parseObject(store.get(nextOffset), CCMessage.class);
        if (message == null) {
            return null;
        }
        message.getHeaders().put("x-offset", nextOffset + "");
        return message;
    }
}
