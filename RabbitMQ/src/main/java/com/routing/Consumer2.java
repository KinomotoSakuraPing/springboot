package com.routing;

import com.RabbitMQClient;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/15 13:56
 */
public class Consumer2 {
    @Test
    public void consumer() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQClient.getConnection();
        //2. 创建channel
        final Channel channel = connection.createChannel();
        //3. 声明队列，Hello-World
        /**
         * 参数1. queue-指定队列名称
         * 参数2. durable-当前队列是否需要持久化
         * 参数3. exchange-是否排外（执行conn.close()之后-当前队列会被自动删除，当前队列只能被一个消费者消费）
         * 参数4. autoDelete-如果这个队列没有消费者在消费，队列自动删除
         * 参数5. arguments-指定当前队列的其他信息
         */
        channel.queueDeclare("routing-queue-info",true,false,false,null);
        //3.5 指定当前消费者，一次消费多少个消息
        channel.basicQos(1);

        //4. 开启监听Queue
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者2接收到消息"+new String(body,"UTF-8"));
                //手动ACK
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        /**
         * 参数1. queue-指定消费哪一个队列
         * 参数2. deliverCallback-指定是否自动ACK（当值为true时，接收消息后会立即高速RabbitMQ）
         * 参数3. consumer-指定消费回调
         */
        channel.basicConsume("routing-queue-info",false,consumer);
        System.out.println("开始监听队列");
        //System.in.read()
        System.in.read();

        //5. 释放资源
        channel.close();
        connection.close();

    }
}
