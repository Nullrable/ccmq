package io.cc.mq.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cc.mq.model.CCMessage;
import io.cc.mq.model.Result;
import io.cc.mq.util.HttpInvoker;
import lombok.NoArgsConstructor;

/**
 * @author nhsoft.lsd
 */
@NoArgsConstructor
public class MQClient {

    private static final String BROKER_URL = "http://127.0.0.1:8787/ccmq";

    public void sub(final String topic, final String cid) {
        System.out.println("sub =====>>>>> " + BROKER_URL + "/sub?topic=" + topic + "&cid=" + cid);
        Result result =  HttpInvoker.get(BROKER_URL + "/sub?topic=" + topic + "&cid=" + cid, Result.class);
        System.out.println("sub =====>>>>> " + result);
    }

    public void unsub(final String topic, final String cid) {
        System.out.println("unsub =====>>>>> " + BROKER_URL + "/unsub?topic=" + topic + "&cid=" + cid);
        Result result =  HttpInvoker.get(BROKER_URL + "/unsub?topic=" + topic + "&cid=" + cid, Result.class);
        System.out.println("unsub =====>>>>> " + result);
    }

    public void send(final String topic, final CCMessage message) {
        System.out.println("send =====>>>>> " + message.toString());
        HttpInvoker.post(BROKER_URL + "/send?topic=" + topic, message);
    }

    public CCMessage recv(final String cid, final String topic) {
        Result<JSONObject> result = HttpInvoker.get(BROKER_URL + "/recv?topic=" + topic + "&cid=" + cid, Result.class);
        System.out.println("recv =====>>>>> " + result);
        return result.getResult().toJavaObject(CCMessage.class);
    }

    public int ack(final String cid, final String topic, final CCMessage message) {

        Result result = HttpInvoker.post(BROKER_URL + "/ack?topic=" + topic + "&cid=" + cid, message);
        System.out.println("ack =====>>>>> " + result);
        return (int) result.getResult();
    }
}
