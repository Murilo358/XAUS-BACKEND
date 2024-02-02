package com.XAUS.Notifications.Orders.Publisher;

import com.XAUS.Models.Orders.Orders;
import com.XAUS.Models.User.Enums.UserRole;

public interface OrdersPublisher {

    public void notifyToUserRole(Orders order, UserRole userRole);
}
