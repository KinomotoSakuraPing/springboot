package com.topic;

import com.RabbitMQClient;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/16 0:56
 */
public class Publisher {
    @Test
    public void publish() throws Exception {
        //1. 获取Connection
        Connection connection = RabbitMQClient.getConnection();
        //2. 创建channel
        Channel channel = connection.createChannel();
        //3. 创建exchange，绑定队列 topic-queue-1和topic-queue-2
        // *-->占位符
        // #-->通配符
        channel.exchangeDeclare("topic-exchange", BuiltinExchangeType.TOPIC);
        channel.queueBind("topic-queue-1","topic-exchange","*.red.*");
        channel.queueBind("topic-queue-2","topic-exchange","fast.#");
        channel.queueBind("topic-queue-2","topic-exchange","*.*.rabbit");
        //4. 发布消息到exchange
        channel.basicPublish("topic-exchange","fast.red.monkey",null,"快红猴".getBytes());
        channel.basicPublish("topic-exchange","slow.black.dog",null,"慢黑狗".getBytes());
        channel.basicPublish("topic-exchange","fast.white.cat",null,"快百猫".getBytes());
        System.out.println("生产者发布消息成功！");

        //5. 释放资源
        channel.close();
        connection.close();
    }
}
