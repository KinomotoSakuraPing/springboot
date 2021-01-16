package com;

import com.rabbitmq.client.Connection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/14 23:32
 */
public class Demo1 {

    @Test
    public void getConnection() throws IOException {
        Connection connection = RabbitMQClient.getConnection();
        System.out.println(connection);
        connection.close();
    }
}