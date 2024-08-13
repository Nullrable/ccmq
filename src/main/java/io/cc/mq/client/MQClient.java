package io.cc.mq.client;

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

    public static void sub(final String topic, final String cid) {
        Result result =  HttpInvoker.get(BROKER_URL + "/sub?topic=" + topic + "&cid=" + cid, Result.class);
        if (result.getCode() != 0) {
            throw new RuntimeException("sub topic/cid error");
        }
        System.out.println("sub =====>>>>> " + BROKER_URL + "/sub?topic=" + topic + "&cid=" + cid);
    }

    public static void unsub(final String topic, final String cid) {
        Result result = HttpInvoker.get(BROKER_URL + "/unsub?topic=" + topic + "&cid=" + cid, Result.class);
        if (result.getCode() != 0) {
            throw new RuntimeException("sub topic/cid error");
        }
        System.out.println("unsub =====>>>>> " + BROKER_URL + "/unsub?topic=" + topic + "&cid=" + cid);
    }

    public static void send(final String topic, final CCMessage message) {
        System.out.println("send =====>>>>> " + message.toString());
        HttpInvoker.post(BROKER_URL + "/send?topic=" + topic, message);
    }

    public static CCMessage recv(final String cid, final String topic) {
        Result<JSONObject> result = HttpInvoker.get(BROKER_URL + "/recv?topic=" + topic + "&cid=" + cid, Result.class);
        if (result.getResult() == null) {
            return null;
        }
        System.out.println("recv =====>>>>> " + result);
        return result.getResult().toJavaObject(CCMessage.class);
    }

    public static CCMessage recv(final String cid, final String topic, final int index) {
        Result<JSONObject> result = HttpInvoker.get(BROKER_URL + "/recv?topic=" + topic + "&cid=" + cid + "&index=" + index, Result.class);
        if (result.getResult() == null) {
            return null;
        }
        System.out.println("recv =====>>>>> " + result);
        return result.getResult().toJavaObject(CCMessage.class);
    }

    public static int ack(final String cid, final String topic, final CCMessage message) {
        Result result = HttpInvoker.post(BROKER_URL + "/ack?topic=" + topic + "&cid=" + cid, message);
        System.out.println("ack =====>>>>> " + result);
        return (int) result.getResult();
    }
}
