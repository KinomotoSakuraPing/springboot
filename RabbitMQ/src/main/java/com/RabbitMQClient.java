package com;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/14 23:28
 */
public class RabbitMQClient {
    public static Connection getConnection(){
        //创建connection工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("ip");
        factory.setPort(5672);
        factory.setUsername("Username");
        factory.setPassword("Password");
        factory.setVirtualHost("/test");
        //创建connection
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }
}