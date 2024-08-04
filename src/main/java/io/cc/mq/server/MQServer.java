package io.cc.mq.server;

import io.cc.mq.model.CCMessage;
import io.cc.mq.model.Result;
import io.cc.mq.model.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nhsoft.lsd
 */
@RestController
public class MQServer {

    private MQManager mqManager = new MQManager();

    @GetMapping("/sub")
    public Result<String> sub(@RequestParam("topic") String topic,
                              @RequestParam("consumer_id") String consumerId) {
        mqManager.sub(new Subscription(topic, consumerId));
        return Result.ok();
    }

    @GetMapping("/unsub")
    public Result<String> unsub(@RequestParam("topic") String topic,
                                @RequestParam("consumer_id") String consumerId) {
        mqManager.unsub(new Subscription(topic, consumerId));
        return Result.ok();
    }

    @GetMapping("/recv")
    public Result<CCMessage<?>> recv(@RequestParam("topic") String topic,
                                     @RequestParam("consumer_id") String consumerId,
                                     @RequestParam("offset") int offset) {
        CCMessage<?> message = mqManager.recv(topic, consumerId, offset);
        return Result.msg(message);
    }

    @PostMapping("/send")
    public Result<String> send(@RequestParam("topic") String topic,
                               @RequestBody CCMessage<?> message) {
        mqManager.send(topic, message);
        return Result.ok();
    }

    @PostMapping("/ack")
    public Result<Integer> ack(@RequestParam("topic") String topic,
                               @RequestParam("consumer_id") String consumerId,
                               @RequestBody CCMessage message) {
        String offset = (String) message.getHeaders().get("x-offset");
        return Result.data(mqManager.ack(topic, consumerId, Integer.parseInt(offset)));
    }

}
