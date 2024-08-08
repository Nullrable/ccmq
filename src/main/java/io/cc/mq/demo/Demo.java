package io.cc.mq.demo;

import com.alibaba.fastjson.JSON;
import io.cc.mq.client.MQClient;
import io.cc.mq.model.CCMessage;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;

/**
 * @author nhsoft.lsd
 */
public class Demo {
    private static final String TOPIC = "org.lsd.test";
    private static final String CID_TEST = "cid_test";
     public static void main(String[] args) {

         MQClient client = new MQClient();

         client.sub(TOPIC, CID_TEST);

         for (int i = 1; i <= 10 ; i++) {
             String msg = JSON.toJSONString(new Order(i, "NO.00" + i, BigDecimal.valueOf(i)));
             client.send(TOPIC, new CCMessage((long) i, msg));
         }

         for (int i = 1; i <= 10 ; i++) {
             CCMessage message = client.recv(TOPIC, CID_TEST);
             client.ack(TOPIC, CID_TEST, message);
         }

         while (true) {
             Scanner scanner = new Scanner(System.in);

             String input = scanner.next();

             if (input.equals("q")) {
                 scanner.close();
                 break;
             }

             if (input.equals("w")) {
                 long id = new Random().nextLong(1, 100);
                 String msg = JSON.toJSONString(new Order(id, "NO.00" + id, BigDecimal.valueOf(id)));
                 client.send(TOPIC, new CCMessage(id, msg));
             }

             if (input.equals("r")) {
                 CCMessage message = client.recv(TOPIC, CID_TEST);
                 client.ack(TOPIC, CID_TEST, message);
             }
         }
    }
}
