package com.example.rabbitmqconsumerdemo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean(name = "simpleContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new org.springframework.amqp.support.converter.SimpleMessageConverter());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    public static final String validationResponseQueueName = "validationResponseQueue";
    public static final String exchangeName = "listings.exchange";

    @Bean
    public Queue validationResponseQueue() {
        return new Queue(validationResponseQueueName, false);
    }

    @Bean
    public TopicExchange listingExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding validationBinding(Queue validationResponseQueue,TopicExchange listingExchange){
        return BindingBuilder.bind(validationResponseQueue).to(listingExchange).with("listings.validate.response");
    }

}
