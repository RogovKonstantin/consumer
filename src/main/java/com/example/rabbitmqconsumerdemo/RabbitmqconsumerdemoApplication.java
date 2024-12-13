package com.example.rabbitmqconsumerdemo;

import com.example.rabbitmqconsumerdemo.dtos.ValidationRequestMessage;
import com.example.rabbitmqconsumerdemo.dtos.ValidationResponseMessage;
import com.example.rabbitmqconsumerdemo.grpc.ListingValidationClient;
import com.example.rabbitmqconsumerdemo.utils.ActionType;
import com.example.rabbitmqconsumerdemo.utils.MessageProcessor;
import com.example.rabbitmqconsumerdemo.websocket.NotificationService;
import com.example.validation.ListingValidationProto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class RabbitmqconsumerdemoApplication {
    @Autowired
    private NotificationService notificationService;
    private final MessageProcessor messageProcessor;
    private final RabbitMQPublisher rabbitMQPublisher;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String updateQueueName = "updateQueue";
    public static final String deleteQueueName = "deleteQueue";
    public static final String validateQueueName = "validateQueue";
    public static final String validationResponseQueueName = "validationResponseQueue";


    @Autowired
    public RabbitmqconsumerdemoApplication(MessageProcessor messageProcessor, RabbitMQPublisher rabbitMQPublisher) {
        this.messageProcessor = messageProcessor;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

    @Bean
    public Queue updateQueue() {
        return new Queue(updateQueueName, false);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteQueueName, false);
    }

    @Bean
    public Queue validateQueue() {
        return new Queue(validateQueueName, false);
    }


    @RabbitListener(queues = updateQueueName, containerFactory = "simpleContainerFactory")
    public void listenUpdate(String message) {
        messageProcessor.processMessage(message, ActionType.UPDATE);
        notificationService.sendNotification(message);
    }

    @RabbitListener(queues = deleteQueueName, containerFactory = "simpleContainerFactory")
    public void listenDelete(String message) {
        messageProcessor.processMessage(message, ActionType.DELETE);
        notificationService.sendNotification(message);
    }

    @RabbitListener(queues = validateQueueName, containerFactory = "rabbitListenerContainerFactory")
    public void handleValidationRequest(ValidationRequestMessage requestMessage) {

        ListingValidationProto.ValidationResponse validationResponse =
                ListingValidationClient.validateListing(requestMessage.getTitle(), requestMessage.getDescription());
        notificationService.sendNotification("Listing " + requestMessage.getListingId() + " sent to validation service");
        ValidationResponseMessage responseMessage = new ValidationResponseMessage(
                requestMessage.getListingId(),
                validationResponse.getIsValid(),
                validationResponse.getMessage()
        );
        notificationService.sendNotification("Listing's" + requestMessage.getListingId() + " " + validationResponse.getMessage());
        rabbitMQPublisher.sendMessage("listings.validate.response", responseMessage);
        notificationService.sendNotification(responseMessage + "sent to main service");
    }


    public static void main(String[] args) {
        SpringApplication.run(RabbitmqconsumerdemoApplication.class, args);
    }
}

