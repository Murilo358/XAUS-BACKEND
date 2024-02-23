package com.XAUS.Configs.WebSockets;

import com.XAUS.Managers.LogManager;
import com.XAUS.Notifications.Orders.Consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketEventListener {

    @Autowired
    Consumer consumer;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

        if(event.getUser() != null){
            if(event.getUser() != null){
                LogManager.logInfo(getClass(), event.getUser() + " has connected to websockets");
            }
        }
        else{
            LogManager.logInfo(getClass(), "A user has connected to websockets");
        }

        consumer.checkNewConnection(event.getUser());
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {

        if(event.getUser() != null) {
            LogManager.logInfo(getClass(), event.getUser().getName() + " Was disconnected from websockets");
        }else {
            LogManager.logInfo(getClass(), "A user was disconnected from websockets");
        }

    }
}
