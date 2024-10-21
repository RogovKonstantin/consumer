package com.example.rabbitmqconsumerdemo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

@SpringBootApplication
public class RabbitmqconsumerdemoApplication {

    public static final String createQueueName = "createQueue";
    public static final String updateQueueName = "updateQueue";
    public static final String deleteQueueName = "deleteQueue";

    @Bean
    public Queue createQueue() {
        return new Queue(createQueueName, true);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueueName, true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteQueueName, true);
    }

    @RabbitListener(queues = createQueueName)
    public void listenCreate(String message) {
        try {
            System.out.println("Message read from createQueue: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message from createQueue: " + e.getMessage());

        }
    }

    @RabbitListener(queues = updateQueueName)
    public void listenUpdate(String message) {
        try {
            System.out.println("Message read from updateQueue: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message from updateQueue: " + e.getMessage());
        }
    }

    @RabbitListener(queues = deleteQueueName)
    public void listenDelete(String message) {
        try {
            System.out.println("Message read from deleteQueue: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message from deleteQueue: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqconsumerdemoApplication.class, args);
    }
}
