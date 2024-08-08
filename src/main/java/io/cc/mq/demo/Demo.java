package io.cc.mq.demo;

import com.alibaba.fastjson.JSON;
import io.cc.mq.client.CCConsumer;
import io.cc.mq.client.CCProducer;
import io.cc.mq.model.CCMessage;
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

         CCProducer producer = new CCProducer();
         CCConsumer consumer = new CCConsumer(CID_TEST);

         consumer.sub(TOPIC);

         for (int i = 1; i <= 10 ; i++) {
             String msg = JSON.toJSONString(new Order(i, "NO.00" + i, BigDecimal.valueOf(i)));
             producer.send(TOPIC, new CCMessage((long) i, msg));
         }

//         for (int i = 1; i <= 10 ; i++) {
//             CCMessage message = consumer.recv();
//             consumer.ack(message);
//         }

         consumer.addListener(TOPIC, (topic, message) -> {
             System.out.println("onMessage =====>>>>> " + message);
             consumer.ack(topic, message);
         });

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
                 producer.send(TOPIC, new CCMessage(id, msg));
             }

             if (input.equals("r")) {
                 CCMessage message = consumer.recv(TOPIC);
                 consumer.ack(TOPIC, message);
             }
         }
    }
}
