package com.helloworld;

import com.RabbitMQClient;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
//        //3. 发布消息到exchange
//        String msg = "Hello-world!";
//        /**
//         * 参数1：指定exchange，使用“”
//         * 参数2：指定路由的规则，使用具体的队列名称
//         * 参数3：指定传递的消息所携带的properties，使用null
//         * 参数4：指定发布的具体消息，byte[]类型
//         */
//        channel.basicPublish("","HelloWorld",null,msg.getBytes());
//        //exchange是不会将消息持久化到本地，Queue才会将消息持久化
//        System.out.println("生产者发布消息成功！");
        //3. 发布消息到exchange
//        //开启confirm（普通Confirm方式）
//        channel.confirmSelect();
//        String msg = "Hello-world!";
//        channel.basicPublish("","HelloWorld",null,msg.getBytes());
//        //判断消息是否发送成功
//        if (channel.waitForConfirms()){
//            System.out.println("生产者发布消息成功！");
//        }else {
//            System.out.println("消息发送失败");
//        }

////3. 发布消息到exchange（批量Confirm方式）
//        //开启confirm
//        channel.confirmSelect();
//        //批量发送消息
//        for (int i = 0; i < 10; i++) {
//            String msg = "Hello-world"+i;
//            channel.basicPublish("","HelloWorld",null,msg.getBytes());
//        }
//
//        //确认消息是否发送成功
//        //当发送的全部消息有一个失败时，就直接全部失败并抛出异常（IOException）
//        channel.waitForConfirmsOrDie();

        //开启return机制
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //当消息没有送达queue是才会执行
                System.out.println(new String(body,"UTF-8")+"没有送达Queue中");
            }
        });
//3. 发布消息到exchange（异步Confirm方式）
        //开启confirm
        channel.confirmSelect();
        //批量发送消息
        for (int i = 0; i < 10; i++) {
            String msg = "Hello-world"+i;
            //消息的构造方法不一样，多一个参数pros开启保证return执行，换个rootingKey将会送达失败
            channel.basicPublish("","HelloWorld",true,null,msg.getBytes());
        }

        //确认消息是否发送成功
        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息发送成功，标识:"+deliveryTag+",是否是批量"+multiple);
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息发送失败，标识:"+deliveryTag+",是否是批量"+multiple);
            }
        });
        System.in.read();
        //4. 释放资源
        channel.close();
        connection.close();
    }
}