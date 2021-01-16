package com.helloworld;

import com.RabbitMQClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/14 23:46
 */
public class Publish {

    @Test
    public void publish() throws Exception {
        //1. 获取Connection
        Connection connection = RabbitMQClient.getConnection();
        //2. 创建channel
        Channel channel = connection.createChannel();
        //3. 发布消息到exchange
        String msg = "Hello-world!";
        /**
         * 参数1：指定exchange，使用“”
         * 参数2：指定路由的规则，使用具体的队列名称
         * 参数3：指定传递的消息所携带的properties，使用null
         * 参数4：指定发布的具体消息，byte[]类型
         */
        channel.basicPublish("","HelloWorld",null,msg.getBytes());
        //exchange是不会将消息持久化到本地，Queue才会将消息持久化
        System.out.println("生产者发布消息成功！");

        //4. 释放资源
        channel.close();
        connection.close();
    }
}