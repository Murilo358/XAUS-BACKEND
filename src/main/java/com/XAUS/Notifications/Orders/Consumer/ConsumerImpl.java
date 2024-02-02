package com.XAUS.Notifications.Orders.Consumer;

import com.XAUS.Managers.LogManager;
import com.XAUS.Models.Orders.Orders;
import com.XAUS.Models.User.Enums.UserRole;
import com.XAUS.Services.WebSocket.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Component
public class ConsumerImpl implements Consumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    WebSocketService webSocketService;

    static Queue<Orders>  cachedOrdersQueue = new ArrayDeque<Orders>();

    private UserRole[] consumerRoles ={ UserRole.PACKAGER, UserRole.ADMIN} ;

    @RabbitListener(queues = "${rabbitmq.queue}")
    @Override
    public void consume(Orders orders) {
        List<SimpUser> simpUser = webSocketService.getConnectionByRole(consumerRoles);

        if(simpUser.isEmpty()){
            LogManager.logInfo(getClass(), "There's no connection to role: " + Arrays.toString(consumerRoles) + " to receive notification, adding to cache...");
            addOrderToCache(orders);
        }else{
        simpUser.forEach(user -> sendNotification(user, orders));
        }


    }

    public void addOrderToCache(Orders orders) {

        cachedOrdersQueue.add(orders);
    }

    public void sendNotification(SimpUser simpUser, Orders orders){

        if (simpUser != null) {
            for (SimpSession session : simpUser.getSessions()) {

                Set<SimpSubscription> simpSubscription = session.getSubscriptions();

                if(!simpSubscription.isEmpty()){
                    String destination = simpSubscription.iterator().next().getDestination().replace("/user", "");

                    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                    headerAccessor.setSessionId(session.getId());
                    headerAccessor.setLeaveMutable(true);

                    try {
                        messagingTemplate.convertAndSendToUser(session.getId(), destination, orders, headerAccessor.getMessageHeaders());
                    } catch (MessagingException e) {
                        LogManager.logError(getClass(), "Error while sending message", e);
                    }

                    LogManager.logInfo(getClass(), "Order notification sent in: " + destination +
                            " to user: " + simpUser.getName() + ", order: " + orders);
                }else{
                    LogManager.logWarn(getClass(), "User: " + simpUser.getName() + " doesn't have any subscription, adding to cache...");
                    addOrderToCache(orders);
                }


            }
        }
    }

    public void checkNewConnection(Principal principalUser){

        SimpUser simpUser = webSocketService.getSimpUserByNameAndRole(principalUser.getName(), consumerRoles);
        try{
            if(simpUser != null){
                if(simpUser.getSessions().iterator().next().getSubscriptions().isEmpty()){
                    //Waits until user has subscriptions if he doesn't in 1 seconds the notifications will no be sent to him
                    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

                    Runnable runnable = new Runnable() {

                        @Override
                        public void run() {
                            while (cachedOrdersQueue.iterator().hasNext()){
                                sendNotification(simpUser,cachedOrdersQueue.remove());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    };
                    executor.schedule(runnable,1, TimeUnit.SECONDS );
                }else{
                    while (cachedOrdersQueue.iterator().hasNext()){
                        sendNotification(simpUser,cachedOrdersQueue.remove());
                    }
                }



            }
        }catch(NoSuchElementException e){
            LogManager.logError(getClass(), "Error while sending notification", e);
        }


    }


}
