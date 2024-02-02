package com.XAUS.Configs.NotificationConfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue}")
    public String applicattionQueueName;


    @Value("${rabbitmq.exchange}")
    public  String applicattionExchange;

    @Value("${rabbitmq.routingkey}")
    public  String applicattionRoutingkey;

    public static String queueName;
    public static String exchange;
    public static String routingkey;

    @Value("${rabbitmq.queue}")
    public void setQueueName(String name){
        RabbitMQConfig.queueName = name;
    }


    @Value("${rabbitmq.exchange}")
    public void setExchange(String exchange){
        RabbitMQConfig.exchange = exchange;
    }

    @Value("${rabbitmq.routingkey}")
    public void setRoutingKey(String routingkey){
        RabbitMQConfig.routingkey = routingkey;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }


}
