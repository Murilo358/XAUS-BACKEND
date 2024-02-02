package com.XAUS.Notifications.Orders.Consumer;

import com.XAUS.Models.Orders.Orders;

import java.security.Principal;

public interface Consumer {

    public void consume (Orders order);

    public void checkNewConnection(Principal principalUser);
}
