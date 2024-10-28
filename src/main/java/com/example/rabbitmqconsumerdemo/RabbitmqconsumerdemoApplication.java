package com.example.rabbitmqconsumerdemo;

import com.example.rabbitmqconsumerdemo.utils.MessageProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqconsumerdemoApplication {

    //TODO time logging and suspicious listings

    private final MessageProcessor messageProcessor;

    public static final String createQueueName = "createQueue";
    public static final String updateQueueName = "updateQueue";
    public static final String deleteQueueName = "deleteQueue";

    @Autowired
    public RabbitmqconsumerdemoApplication(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

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
        messageProcessor.processCreateMessage(message);
    }

    @RabbitListener(queues = updateQueueName)
    public void listenUpdate(String message) {
        messageProcessor.processUpdateMessage(message);
    }

    @RabbitListener(queues = deleteQueueName)
    public void listenDelete(String message) {
        messageProcessor.processDeleteMessage(message);
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqconsumerdemoApplication.class, args);
    }
}

