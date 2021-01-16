package com.springbootrabbitmq.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chenpingping
 * @version 1.0
 * @date 2021/1/16 21:44
 */
@Component
public class PublishConfirmAndReturn implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initMethod(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(6);
        if (ack){
            System.out.println("消息已经送达exchange");
        }else {
            System.out.println("消息没有送到exchange");
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("消息没有送达queue");
    }
}