package com.example.rabbitmqconsumerdemo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqconsumerdemoApplication {

    public static final String createQueueName = "createQueue";
    public static final String updateQueueName = "updateQueue";
    public static final String deleteQueueName = "deleteQueue";

    @Bean
    public Queue createQueue() {
        return new Queue(createQueueName, false);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueueName, false);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteQueueName, false);
    }

    @RabbitListener(queues = createQueueName)
    public void listenCreate(String message) {
        System.out.println("Message read from createQueue: " + message);
    }

    @RabbitListener(queues = updateQueueName)
    public void listenUpdate(String message) {
        System.out.println("Message read from updateQueue: " + message);
    }

    @RabbitListener(queues = deleteQueueName)
    public void listenDelete(String message) {
        System.out.println("Message read from deleteQueue: " + message);
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqconsumerdemoApplication.class, args);
    }
}
