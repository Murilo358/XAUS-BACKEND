package com.XAUS.Notifications.Orders.Publisher;

import com.XAUS.Configs.NotificationConfig.RabbitMQConfig;

import com.XAUS.Models.Orders.Orders;
import com.XAUS.Models.User.Enums.UserRole;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersPublisherImpl implements OrdersPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Override
    public void notifyToUserRole(Orders order, UserRole userRoleToSend) {
            amqpTemplate.convertAndSend(RabbitMQConfig.exchange,RabbitMQConfig.routingkey,order);

    }
}
