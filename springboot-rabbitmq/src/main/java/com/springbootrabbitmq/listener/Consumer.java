package com.springbootrabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/16 20:09
 */
@Component
public class Consumer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(queues = "boot-queue")
    public void getMassage(String msg, Channel channel, Message message) throws IOException {
        //0. 获取MessageId
        String messageId = message.getMessageProperties().getMessageId();
        //1. 设置key到redis
        if (redisTemplate.opsForValue().setIfAbsent(messageId, "0", 10, TimeUnit.SECONDS)) {
            //2. 消费消息
            System.out.println("消费消息");
            //3. 设置key的value为1
            redisTemplate.opsForValue().set(messageId, "1");
            //4. 手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            //5. 获取redis中的value即可，如果是1，手动ack
            if ("1".equalsIgnoreCase(redisTemplate.opsForValue().get(messageId))) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
    }
}