package com.routing;

import com.RabbitMQClient;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/16 0:31
 */
public class Publisher {
    @Test
    public void publish() throws Exception {
        //1. 获取Connection
        Connection connection = RabbitMQClient.getConnection();
        //2. 创建channel
        Channel channel = connection.createChannel();
        //3. 创建exchange，routing-queue-error,routing-queue-info
        channel.exchangeDeclare("routing-exchange", BuiltinExchangeType.DIRECT);
        channel.queueBind("routing-queue-error","routing-exchange","ERROR");
        channel.queueBind("routing-queue-info","routing-exchange","INFO");
        //4. 发布消息到exchange
        channel.basicPublish("routing-exchange","ERROR",null,"ERROR".getBytes());
        channel.basicPublish("routing-exchange","INFO",null,"INFO1".getBytes());
        channel.basicPublish("routing-exchange","INFO",null,"INFO2".getBytes());
        channel.basicPublish("routing-exchange","INFO",null,"INFO3".getBytes());
        System.out.println("生产者发布消息成功！");

        //5. 释放资源
        channel.close();
        connection.close();
    }
}
