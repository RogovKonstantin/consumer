package com.example.rabbitmqconsumerdemo;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String routingKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.exchangeName, routingKey, message);
            System.out.println("Message sent successfully: " + message);
        } catch (AmqpException e) {
            System.err.println("Failed to send message: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("Unexpected error occurred while sending message" + e.getMessage());

        }
    }


}
