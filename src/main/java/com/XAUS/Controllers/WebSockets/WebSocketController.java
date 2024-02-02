package com.XAUS.Controllers.WebSockets;

import com.XAUS.Models.Orders.Orders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/newOrder")
    @SendTo("/topic/notify")
    public String broadcastMessage(@Payload Orders order) {
        System.out.println("Received on websocket " + order.getProducts());
        return "You have received a order from front-end: " + order.getProducts();
    }

}